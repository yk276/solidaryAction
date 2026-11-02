package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OngController {

    @FXML
    private TextField txtNomeFantasia;

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtCnpj;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtEmailAdmin;

    @FXML
    private PasswordField txtSenhaAdmin;

    @FXML
    public void salvarOng(ActionEvent event) throws Exception {

        if (txtEmailAdmin.getText().trim().isEmpty()
                || txtSenhaAdmin.getText().trim().isEmpty()) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Informe o email e a senha do administrador!"
            );
            return;
        }

        String token = autenticarAdministrador();

        if (token == null) {
            return;
        }

        String jsonRequest = String.format(
                "{\n" +
                        "  \"nomeFantasia\": \"%s\",\n" +
                        "  \"razaoSocial\": \"%s\",\n" +
                        "  \"cnpj\": \"%s\",\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"endereco\": \"%s\",\n" +
                        "  \"secretKey\": \"%s\"\n" +
                        "}",
                prepararTextoJson(txtNomeFantasia.getText()),
                prepararTextoJson(txtRazaoSocial.getText()),
                prepararTextoJson(txtCnpj.getText()),
                prepararTextoJson(txtEmail.getText()),
                prepararTextoJson(txtEndereco.getText()),
                "qhwdquq6ed78wesdll"
        );

        int respostaApi = executaMetodoAPI(
                "http://localhost:8080/ongs/admin",
                jsonRequest,
                "POST",
                token
        );

        System.out.println("Status ao salvar ONG: " + respostaApi);

        if (respostaApi == 200) {
            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Ong salva com sucesso!"
            );
            onVoltarAoMenu(event);

        } else if (respostaApi == 403) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Somente o administrador pode cadastrar ONGs!"
            );

        } else if (respostaApi == 401) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Autenticação inválida. Confira os dados do administrador!"
            );

        } else {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Erro ao salvar ong! Código: " + respostaApi
            );
        }
    }

    //Faz login na API e obtém o token retornado.
    private String autenticarAdministrador() throws Exception {

        String jsonRequest = String.format(
                "{\"email\":\"%s\",\"senha\":\"%s\"}",
                prepararTextoJson(txtEmailAdmin.getText()),
                prepararTextoJson(txtSenhaAdmin.getText())
        );

        URL url = new URL("http://localhost:8080/auth/login");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
        }

        if (connection.getResponseCode() != 200) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Não foi possível autenticar. Confira o email e a senha!"
            );
            connection.disconnect();
            return null;
        }

        StringBuilder textoResposta = new StringBuilder();

        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream(),
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;

            while ((linha = entrada.readLine()) != null) {
                textoResposta.append(linha);
            }
        }

        String resposta = textoResposta.toString();

        connection.disconnect();

        //Localiza o campo token no JSON retornado pela API.
        Matcher resultado = Pattern.compile(
                "\"token\"\\s*:\\s*\"([^\"]+)\""
        ).matcher(resposta);

        if (resultado.find()) {
            return resultado.group(1);
        }

        showMessage(
                Alert.AlertType.ERROR,
                "A API não retornou um token de acesso!"
        );

        return null;
    }

    @FXML
    public void onVoltarAoMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/back/menu-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

        stage.setScene(scene);
    }

    //Envia a requisição com o token obtido no login.
    private int executaMetodoAPI(
            String url,
            String json,
            String protocoloHttp,
            String token
    ) throws Exception {

        URL urlAPI = new URL(url);

        HttpURLConnection connection =
                (HttpURLConnection) urlAPI.openConnection();

        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);
        connection.setRequestProperty(
                "Authorization",
                "Bearer " + token
        );

        if (!json.isEmpty()) {
            connection.setRequestProperty(
                    "Content-Type",
                    "application/json; charset=UTF-8"
            );

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
        }

        int status = connection.getResponseCode();

        connection.disconnect();

        return status;
    }

    //Prepara os caracteres especiais dos textos enviados no JSON.
    private String prepararTextoJson(String texto) {
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void showMessage(Alert.AlertType type, String msg) {

        Alert alerta = new Alert(type);

        alerta.setTitle("Mensagem do Sistema");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}