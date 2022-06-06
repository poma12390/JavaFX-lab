package lab6.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import lab6.client.commands.Utils;
import lab6.gui.main.MainFrame;
import lab6.gui.main.Shake;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RegisterFrame {

    public static BlockingDeque<String> responses = null;


    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;
    @FXML
    private TextFlow registerFlow;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Button registerButton;


    @FXML
    private TextFlow errorFlow;
    @FXML
    private ChoiceBox<String> selectLanguage;

    private final String[] languages = {"ru","eng", "lt", "et"};

    private static Text curText;

    private volatile static Text errorText;

    private static String curError;


    @FXML
    void initialize() {
        responses = new LinkedBlockingDeque<>();
        selectLanguage.getItems().addAll(languages);
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

        });
        loginButton.setOnAction(event -> {
            loginButton.getScene().getWindow().hide();
            AuthFrame authFrame = new AuthFrame();
            authFrame.start();
        });
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });

        curText=Controller.getTitleText();
        errorText = Controller.getErrorText();
        localization(PropertyWorker.getLanguage());
        responseReceiver();

    }


    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        curText.setText(PropertyWorker.getBundle().getString("titleReg"));
        loginField.setPromptText(PropertyWorker.getBundle().getString("login"));
        passwordField.setPromptText(PropertyWorker.getBundle().getString("password"));
        passwordField1.setPromptText(PropertyWorker.getBundle().getString("repeatSingInButton"));
        loginButton.setText(PropertyWorker.getBundle().getString("logInButton"));
        registerButton.setText(PropertyWorker.getBundle().getString("signUpButton"));
        registerFlow.getChildren().clear();
        registerFlow.getChildren().add(curText);
        selectLanguage.setValue(language);
        errorFlow.getChildren().clear();
        errorFlow.getChildren().add(new Text(""));
        if (curError != null) {
            errorText.setText(PropertyWorker.getBundle().getString(curError));
            errorFlow.getChildren().add(errorText);
        }
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
                    errorFlow.getChildren().clear();
                });
                if (response.equals("success")) {
                    curError=null;
                    Platform.runLater(() -> {
                        loginButton.getScene().getWindow().hide();
                    });
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.start();
                } else {
                    Shake.Shake(loginField);
                    Shake.Shake(passwordField);
                    Shake.Shake(passwordField1);
                    curError = response;

                    errorText.setText(PropertyWorker.getBundle().getString(response));
                    Text text = Controller.getErrorText();
                    text.setText(PropertyWorker.getBundle().getString(curError));
                    PropertyWorker.getBundle().getString(curError);
                    System.out.println(response);
                    System.out.println(PropertyWorker.getBundle().getString(response));
                    Platform.runLater(() -> {
                        System.out.println(text);
                        errorFlow.getChildren().clear();
                        errorFlow.getChildren().add(text);

                    });
                }
            }

        }).start();
    }


}

