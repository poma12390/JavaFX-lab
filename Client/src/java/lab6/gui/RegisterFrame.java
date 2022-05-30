package lab6.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab6.client.ClientRunner;
import lab6.client.commands.Utils;

public class RegisterFrame {


    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Button registerButton;

    @FXML
    private Label wrongPass;


    @FXML
    void initialize(){
        registerButton.setOnAction(event -> {
            if (passwordField.getText().isEmpty() || passwordField1.getText().isEmpty()){
                wrongPass.setText("password cannot be empty");
                wrongPass.setVisible(true);
            }
            else if (loginField.getText().isEmpty()){
                wrongPass.setText("login cannot be empty");
                wrongPass.setVisible(true);
            }
            else if  (! passwordField1.getText().equals(passwordField.getText())){
                wrongPass.setText("Пароли не совпадают");
                wrongPass.setVisible(true);
                //Не одинаковые пароли

            }
            else {
                Utils.runCommandFromString("register " + loginField.getText() + " " + passwordField.getText());
                AuthFrame authFrame = new AuthFrame();
                registerButton.disarm();
                registerButton.getScene().getWindow().hide();

                try {
                    authFrame.start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
        loginButton.setOnAction(event -> {
            AuthFrame authFrame = new AuthFrame();
            loginButton.getScene().getWindow().hide();
            authFrame.start();
        });
    }


}
