package lab6.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class AuthFrame {

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

    private final String[] languages = {"eng", "ru"};

    @FXML // fx:id="registerButton"
    private Button registerButton; // Value injected by FXMLLoader
    public void start() {
        Stage authStage = Controller.openWindow("../gui/assets/authFrame.fxml");
        authStage.setTitle(PropertyWorker.getBundle().getString("titleAuth"));
        authStage.show();

    }

    @FXML
    void initialize(){
        selectLanguage.getItems().addAll(languages);
        selectLanguage.setValue("eng");
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
    }

    public void localization(String language){
        PropertyWorker.setNewBundle(language);
        System.out.println(PropertyWorker.getBundle().getString("titleAuth"));

    }


}
