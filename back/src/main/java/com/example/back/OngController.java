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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
    private TextField txtNomeCoordenador;

    @FXML
    private TextField txtCpfCoordenador;

    @FXML
    private TextField txtEmailCoordenador;

    @FXML
    private PasswordField txtSenhaCoordenador;

    private String mensagemErroApi = "";

    //Envia o cadastro da ONG e de seu novo coordenador.
    @FXML
    public void salvarOng(ActionEvent event) {

        if (txtNomeFantasia.getText().trim().isEmpty()
                || txtRazaoSocial.getText().trim().isEmpty()
                || txtCnpj.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtEndereco.getText().trim().isEmpty()
                || txtNomeCoordenador.getText().trim().isEmpty()
                || txtCpfCoordenador.getText().trim().isEmpty()
                || txtEmailCoordenador.getText().trim().isEmpty()
                || txtSenhaCoordenador.getText().trim().isEmpty()) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Preencha todos os campos!"
            );
            return;
        }

        try {
            String jsonRequest = String.format(
                    "{"
                            + "\"nomeFantasia\":\"%s\","
                            + "\"razaoSocial\":\"%s\","
                            + "\"cnpj\":\"%s\","
                            + "\"email\":\"%s\","
                            + "\"endereco\":\"%s\","
                            + "\"nomeCoordenador\":\"%s\","
                            + "\"cpfCoordenador\":\"%s\","
                            + "\"emailCoordenador\":\"%s\","
                            + "\"senhaCoordenador\":\"%s\","
                            + "\"secretKey\":\"%s\""
                            + "}",
                    prepararTextoJson(txtNomeFantasia.getText()),
                    prepararTextoJson(txtRazaoSocial.getText()),
                    prepararTextoJson(txtCnpj.getText()),
                    prepararTextoJson(txtEmail.getText()),
                    prepararTextoJson(txtEndereco.getText()),
                    prepararTextoJson(txtNomeCoordenador.getText()),
                    prepararTextoJson(txtCpfCoordenador.getText()),
                    prepararTextoJson(txtEmailCoordenador.getText()),
                    prepararTextoJson(txtSenhaCoordenador.getText()),
                    "qhwdquq6ed78wesdll"
            );

            int respostaApi = executaMetodoAPI(
                    "http://localhost:8080/ongs/cadastro-com-coordenador",
                    jsonRequest,
                    "POST"
            );

            if (respostaApi == 200) {
                showMessage(
                        Alert.AlertType.INFORMATION,
                        "ONG e coordenador cadastrados com sucesso!"
                );

                onVoltarAoMenu(event);

            } else if (respostaApi == 403) {
                showMessage(
                        Alert.AlertType.ERROR,
                        "Chave de cadastro inválida!"
                );

            } else if (respostaApi == 400) {

                if (mensagemErroApi.isEmpty()) {
                    showMessage(
                            Alert.AlertType.ERROR,
                            "Confira os dados informados, incluindo "
                                    + "o CNPJ da ONG e o CPF do coordenador."
                    );
                } else {
                    showMessage(
                            Alert.AlertType.ERROR,
                            mensagemErroApi
                    );
                }

            } else {
                showMessage(
                        Alert.AlertType.ERROR,
                        "Não foi possível concluir o cadastro. Código: "
                                + respostaApi
                );
            }

        } catch (Exception e) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Não foi possível concluir a operação. "
                            + "Confira se o backend está em execução. "
                            + "Se houve perda de conexão, consulte os "
                            + "registros antes de tentar novamente."
            );
        }
    }

    @FXML
    public void onVoltarAoMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/com/example/back/menu-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(scene);
    }

    //Envia os dados e recupera a mensagem em caso de erro.
    private int executaMetodoAPI(
            String url,
            String json,
            String protocoloHttp
    ) throws Exception {

        mensagemErroApi = "";

        URL urlAPI = new URL(url);

        HttpURLConnection connection =
                (HttpURLConnection) urlAPI.openConnection();

        try {
            connection.setRequestMethod(protocoloHttp);
            connection.setDoOutput(true);

            connection.setRequestProperty(
                    "Content-Type",
                    "application/json; charset=UTF-8"
            );

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int status = connection.getResponseCode();

            if (status >= 400) {
                mensagemErroApi = lerResposta(
                        connection.getErrorStream()
                );
            }

            return status;

        } finally {
            connection.disconnect();
        }
    }

    //Lê o texto devolvido pelo backend.
    private String lerResposta(InputStream entrada) throws IOException {

        if (entrada == null) {
            return "";
        }

        StringBuilder resposta = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(
                        entrada,
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
        }

        return resposta.toString();
    }

    //Prepara os caracteres especiais para envio no JSON.
    private String prepararTextoJson(String texto) {

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void showMessage(
            Alert.AlertType type,
            String msg
    ) {
        Alert alerta = new Alert(type);

        alerta.setTitle("Mensagem do Sistema");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}