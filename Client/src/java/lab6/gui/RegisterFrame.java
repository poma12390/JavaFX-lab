package lab6.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lab6.client.commands.Utils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RegisterFrame {

    public static BlockingDeque<String> responses = null;


    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;
    @FXML
    private Label regTitle;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Button registerButton;


    @FXML
    private Label wrongPass;
    @FXML
    private ChoiceBox<String> selectLanguage;

    private final String[] languages = {"eng", "ru"};


    @FXML
    void initialize() {
        responses = new LinkedBlockingDeque<>();
        selectLanguage.getItems().addAll(languages);
        localization(PropertyWorker.getLanguage());

        registerButton.setOnAction(event -> {
            if (passwordField!=null) passwordField.setText(passwordField.getText().trim());
            if (passwordField1!=null) passwordField1.setText(passwordField1.getText().trim());
            if (loginField!=null) loginField.setText(loginField.getText().trim());

            if (passwordField.getText().isEmpty() || passwordField1.getText().isEmpty()) {
                responses.add("emptyPassword");
            } else if (loginField.getText().isEmpty()) {
                responses.add("emptyUsername");
            } else if (!passwordField1.getText().equals(passwordField.getText())) {
                responses.add("notSamePasswords");
                //Не одинаковые пароли
            } else {
                new Thread(() -> {
                    Utils.runCommandFromString("register " + loginField.getText() + " " + passwordField.getText());
                    // выполнение в отдельном потоке

                }).start();

            }
            responseReceiver();
        });
        loginButton.setOnAction(event -> {
            loginButton.getScene().getWindow().hide();
            AuthFrame authFrame = new AuthFrame();
            authFrame.start();
        });
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });
    }


    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        loginField.setPromptText(PropertyWorker.getBundle().getString("login"));
        passwordField.setPromptText(PropertyWorker.getBundle().getString("password"));
        passwordField1.setPromptText(PropertyWorker.getBundle().getString("repeatSingInButton"));
        loginButton.setText(PropertyWorker.getBundle().getString("logInButton"));
        registerButton.setText(PropertyWorker.getBundle().getString("signUpButton"));
        regTitle.setText(PropertyWorker.getBundle().getString("titleReg"));
        selectLanguage.setValue(language);
        wrongPass.setText("");
    }

    private void responseReceiver() {
        new Thread(() -> {
            while (true) {
                String response;
                try {
                    response = RegisterFrame.responses.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(() -> {
                    if (response.equals("success")) {
                        loginButton.getScene().getWindow().hide();
                        AuthFrame authFrame = new AuthFrame();
                        authFrame.start();
                    } else {
                        wrongPass.setText(PropertyWorker.getBundle().getString(response));
                    }
                });
            }
            // выполнение в отдельном потоке

        }).start();
    }


}

