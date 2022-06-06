package lab6.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import lab6.client.commands.Utils;
import lab6.gui.main.Shake;
import lab6.gui.main.MainFrame;

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
    private TextFlow authFlow;


    @FXML
    private TextFlow errorFlow;

    private final String[] languages = {"ru","eng", "lt", "et"};

    private volatile static String curError;

    private volatile static Text curText;

    private volatile static Text errorText;

    @FXML // fx:id="registerButton"
    private Button registerButton; // Value injected by FXMLLoader


    public void start() {


        Stage authStage = Controller.openWindow("../gui/assets/authFrame.fxml");
        authStage.show();


    }

    @FXML
    void initialize() {

        responses = new LinkedBlockingDeque<>();
        selectLanguage.getItems().addAll(languages);

        registerButton.setOnAction(event -> {
            registerButton.disarm();
            registerButton.getScene().getWindow().hide();
            Stage registerStage = Controller.openWindow("../gui/assets/registerFrame.fxml");
            registerStage.show();
        });
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });
        loginButton.setOnAction(event -> {
            if (loginField.getText().isEmpty()) {
                blockButtons();
                responses.add("emptyUsername");
                enableButtons();
            } else if (passwordField.getText().isEmpty()) {
                blockButtons();
                responses.add("emptyPassword");
                enableButtons();
            } else {
                new Thread(() -> {
                    blockButtons();
                    Utils.runCommandFromString("auth " + loginField.getText() + " " + passwordField.getText());
                    enableButtons();
                }).start();

            }
        });

        curText=Controller.getTitleText();
        errorText = Controller.getErrorText();

        responseReceiver();
        localization(PropertyWorker.getLanguage());
    }
    private void blockButtons(){
        loginButton.setDisable(true);
        registerButton.setDisable(true);
    }

    private void enableButtons(){
        loginButton.setDisable(false);
        registerButton.setDisable(false);
    }


    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        curText.setText(PropertyWorker.getBundle().getString("titleAuth"));
        authFlow.getChildren().clear();
        authFlow.getChildren().add(curText);
        loginField.setPromptText(PropertyWorker.getBundle().getString("login"));
        passwordField.setPromptText(PropertyWorker.getBundle().getString("password"));
        loginButton.setText(PropertyWorker.getBundle().getString("logInButton"));
        registerButton.setText(PropertyWorker.getBundle().getString("signUpButton"));
        selectLanguage.setValue(PropertyWorker.getLanguage());
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
                    response = responses.take();
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
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.start();
                    });
                } else {
                    Shake.Shake(loginField);
                    Shake.Shake(passwordField);
                    curError = response;
                    errorText.setText(PropertyWorker.getBundle().getString(curError));
                    //Text text  = Controller.getErrorText();
                    Platform.runLater(() -> {
                        errorFlow.getChildren().clear();
                        errorFlow.getChildren().add(errorText);
                    });
                }

            }
            // выполнение в отдельном потоке

        }).start();
    }


}
