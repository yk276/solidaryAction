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
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private TextField txtTotalArrecadado;

    @FXML
    public void salvarOng(ActionEvent event) throws Exception{

        //Validar Dados
        String jsonRequest = String.format("{\n" +
                        "  \"nomeFantasia\": \"%s\",\n" +
                        "  \"razaoSocial\": \"%s\",\n" +
                        "  \"cnpj\": \"%s\",\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"endereco\": \"%s\",\n" +
                        "  \"totalArrecadado\": \"%s\",\n" +
                        "  \"secretKey\": \"%s\"\n" +
                        "}", txtNomeFantasia.getText(),
                txtRazaoSocial.getText(),
                txtCnpj.getText(),
                txtEmail.getText(),
                txtEndereco.getText(),
                txtTotalArrecadado.getText(),
                "qhwdquq6ed78wesdll"
        );

        int respostaApi = executaMetodoAPI("http://localhost:8080/ongs/admin",jsonRequest,"POST");


        if(respostaApi == 200){
            showMessage(Alert.AlertType.INFORMATION,"Ong salva com sucesso!");
            onVoltarAoMenu(event);
        }else{
            showMessage(Alert.AlertType.ERROR,"Erro ao salvar ong!");
        }
    }


    @FXML
    public void onVoltarAoMenu(ActionEvent event) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    //json = patch,post,put
    private int executaMetodoAPI(String url, String json, String protocoloHttp) throws Exception {

        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);

        if(!json.isEmpty()){
            connection.setRequestProperty("Content-Type", "application/json");

            try(OutputStream os = connection.getOutputStream()){
                os.write(json.getBytes());
            }
        }

        return connection.getResponseCode();
    }

    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta = new Alert(type);

        alerta.setTitle("Mensagem do Sistema");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}
