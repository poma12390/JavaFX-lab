package lab6.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lab6.client.commands.Utils;
import lab6.gui.main.MainFrame;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AuthFrame {

    public static BlockingDeque<String> responses = null;
    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="loginField"
    private TextField loginField; // Value injected by FXMLLoader

    @FXML // fx:id="reg"
    private Label reg; // Value injected by FXMLLoader


    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML
    private ChoiceBox<String> selectLanguage;

    @FXML
    private Label authTitle;

    @FXML
    private Label errorLabel;

    private final String[] languages = {"eng", "ru"};

    @FXML // fx:id="registerButton"
    private Button registerButton; // Value injected by FXMLLoader

    public void start() {
        Stage authStage = Controller.openWindow("../gui/assets/authFrame.fxml");
        authStage.setTitle(PropertyWorker.getBundle().getString("titleAuth"));

        authStage.show();

    }

    @FXML
    void initialize() {
        responses = new LinkedBlockingDeque<>();
        selectLanguage.getItems().addAll(languages);
        localization(PropertyWorker.getLanguage());
        registerButton.setOnAction(event -> {
            registerButton.disarm();
            registerButton.getScene().getWindow().hide();
            Stage registerStage = Controller.openWindow("../gui/assets/registerFrame.fxml");
            registerStage.setTitle(PropertyWorker.getBundle().getString("titleReg"));
            registerStage.show();
        });
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });
        loginButton.setOnAction(event -> {
            if (loginField.getText().isEmpty()) {
                errorLabel.setText(PropertyWorker.getBundle().getString("emptyUsername"));
            } else if (passwordField.getText().isEmpty()) {
                errorLabel.setText(PropertyWorker.getBundle().getString("emptyPassword"));
            } else {
                Utils.runCommandFromString("auth " + loginField.getText() + " " + passwordField.getText());
            }
        });
        responseReceiver();
    }


    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        authTitle.setText(PropertyWorker.getBundle().getString("titleAuth"));
        loginField.setPromptText(PropertyWorker.getBundle().getString("login"));
        passwordField.setPromptText(PropertyWorker.getBundle().getString("password"));
        loginButton.setText(PropertyWorker.getBundle().getString("logInButton"));
        registerButton.setText(PropertyWorker.getBundle().getString("signUpButton"));
        selectLanguage.setValue(PropertyWorker.getLanguage());
        errorLabel.setText("");
        System.out.println(PropertyWorker.getBundle().getString("titleAuth"));

    }

    private void responseReceiver() {
        new Thread(() -> {
            while (true) {
                String response;
                try {
                    response = responses.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(() -> {
                    if (response.equals("success")) {
                        loginButton.getScene().getWindow().hide();
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.start();
                    } else {
                        errorLabel.setText(PropertyWorker.getBundle().getString(response));
                    }
                });
            }
            // выполнение в отдельном потоке

        }).start();
    }


}
