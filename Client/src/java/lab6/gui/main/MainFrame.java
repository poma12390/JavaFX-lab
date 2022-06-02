package lab6.gui.main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lab6.client.commands.Utils;
import lab6.gui.AuthFrame;
import lab6.gui.Controller;
import lab6.gui.PropertyWorker;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class MainFrame {

    @FXML
    private TableColumn<?, ?> CreateDate;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField endDateField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField heightField;

    @FXML
    private TextField nameField;
    @FXML
    private TextField salaryField;

    @FXML
    private Button addWorkerButton;

    @FXML
    private ChoiceBox<String> positionChoice;

    @FXML
    private Button quitButton;

    @FXML
    private Button quitButton1;

    @FXML
    private ChoiceBox<String> selectLanguage;

    @FXML
    private TextField startDateField;

    @FXML
    private TableView<?> table;

    @FXML
    private TextField weightField;

    @FXML
    private Label wrongPass;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;
    private final String[] languages = {"eng", "ru"};

    private final String[] positions = {"BAKER", "ENGINEER" ,"LABORER" , "MANAGER", "DIRECTOR"};

    public static BlockingDeque<String> errors = null;


    public void start(){
        Stage mainStage = Controller.openWindow("../gui/assets/mainApp.fxml");
        mainStage.setTitle(PropertyWorker.getBundle().getString("titleAuth"));
        mainStage.show();
    }

    @FXML
    void initialize(){
        positionChoice.getItems().addAll(positions);
        errors = new LinkedBlockingDeque<>();
        selectLanguage.getItems().addAll(languages);
        quitButton.setOnAction(event -> {
            quitButton.getScene().getWindow().hide();
            AuthFrame authFrame = new AuthFrame();
            authFrame.start();
        });
        addWorkerButton.setOnAction(event -> {
            errorLabel.setText("");
            System.out.println(startDateField.getText());
            System.out.println(endDateField.getText());
            System.out.println(("add " + nameField.getText() + " " + xField.getText() + " " + yField.getText()+ " " +salaryField.getText()+ " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue()));
            Utils.runCommandFromString("add " + nameField.getText() + " " + xField.getText() + " " + yField.getText()+ " " +salaryField.getText()+ " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue());
        });

        // autoUpdate();
        errorsReceiver();



    }

    private void autoUpdate(){
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Utils.runCommandFromString("show");
            }

        }).start();
    }

    private void errorsReceiver() {
        new Thread(() -> {
            while (true) {
                String response;
                try {
                    response = MainFrame.errors.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(() -> {
                    errorLabel.setText(response);
                });
            }

        }).start();
    }
}