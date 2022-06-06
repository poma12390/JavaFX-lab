package lab6.gui.main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab6.client.setterrs.SetData;
import lab6.client.setterrs.SetSalary;
import lab6.common.Worker;
import lab6.common.exceptions.InvalidEndDateFormatException;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.gui.Controller;
import lab6.gui.PropertyWorker;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class CheckOutFrame {

    @FXML
    private Label errorLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button inputButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField inputField;

    @FXML
    private Label inputLabel;

    private static String text;


    static Integer testInt;
    static Float testFloat;

    static Date testDate;

    static String testString;

    static Boolean testBoolean = false;
    public static BlockingDeque<Integer> ints = null;
    public static BlockingDeque<Float> floats = null;
    public static BlockingDeque<Date> dates = null;

    Stage checkOutStage;

    boolean gameStarted = false;

    public static BlockingDeque<String> errors = null;

    public void checkOutFloat(String input) {
        text = input;
        checkOutStage = Controller.openWindow("../gui/assets/checkout.fxml");
        checkOutStage.show();
        testFloat = 1F;
    }

    public void checkOutInteger(String input) {
        text = input;
        checkOutStage = Controller.openWindow("../gui/assets/checkout.fxml");
        checkOutStage.show();
        testInt = 1;
    }

    public void checkOutDate(String input) {
        text = input;
        checkOutStage = Controller.openWindow("../gui/assets/checkout.fxml");
        checkOutStage.show();
        testDate = new Date();
    }

    public void checkOutString(String input) {
        text = input;
        checkOutStage = Controller.openWindow("../gui/assets/checkout.fxml");
        checkOutStage.show();
        testString = "123";
    }

    public void checkOutBoolean(String input) {
        text = input;
        checkOutStage = Controller.openWindow("../gui/assets/checkout.fxml");
        checkOutStage.show();
        testBoolean = true;
    }


    @FXML
    void initialize() {
        cancelButton.setText(PropertyWorker.getBundle().getString("cancel"));
        inputButton.setText(PropertyWorker.getBundle().getString("input"));
        errors = new LinkedBlockingDeque<>();
        ints = new LinkedBlockingDeque<>();
        floats = new LinkedBlockingDeque<>();
        dates = new LinkedBlockingDeque<>();

        inputLabel.setText(PropertyWorker.getBundle().getString("input") + " " + PropertyWorker.getBundle().getString(text));
        confirmButton.setText(PropertyWorker.getBundle().getString("gameButton"));
        inputButton.setOnAction(event -> {
            if (testFloat != null) {
                Worker bum = new Worker("123");
                try {
                    SetSalary.setSalary(inputField.getText(), bum);
                    MainFrame.floatInputs.add(bum.getSalary());
                    testFloat = null;
                    inputButton.getScene().getWindow().hide();
                } catch (InvalidSalaryException ignored) {
                }
            } else if (testInt != null) {
                try {

                    Integer integer = Integer.parseInt(inputField.getText());
                    MainFrame.integersInputs.add(integer);
                    testInt = null;
                    inputButton.getScene().getWindow().hide();
                } catch (Exception e) {
                    errors.add("invalidIdFormat");
                }
            } else if (testDate != null) {
                Worker bum = new Worker("123");
                try {
                    SetData.setEndData1(inputField.getText(), bum);
                    MainFrame.dateInputs.add(bum.getEndDate());
                    testDate = null;
                    inputButton.getScene().getWindow().hide();
                } catch (InvalidEndDateFormatException | ParseException ignored) {
                }
            } else if (testString != null) {
                if (inputField.getText().equals("OK")) {
                    inputButton.setVisible(false);
                    inputField.setVisible(false);
                    confirmButton.setVisible(true);
                    cancelButton.setVisible(false);
                    inputLabel.setText(PropertyWorker.getBundle().getString("game").replace("+", "\r\n"));
                }

            } else if (testBoolean) {
                if (inputField.getText().equals("OK")) {
                    MainFrame.booleanInputs.add(true);
                    testBoolean = false;
                    inputButton.getScene().getWindow().hide();
                }
            }
        });
        cancelButton.setOnAction(event -> {
            if (testFloat != null) {
                MainFrame.floatInputs.add(0f);
                testFloat = null;
            } else if (testInt != null) {
                MainFrame.integersInputs.add(0);
                testInt = null;
            } else if (testDate != null) {
                MainFrame.dateInputs.add(new Date(0));
                testDate = null;
            } else if (testString != null) {
                MainFrame.confirmInputs.add("no");
                testString = null;
            } else if (testBoolean) {
                MainFrame.booleanInputs.add(false);
                testBoolean = false;
            }
            errors.clear();
            inputButton.getScene().getWindow().hide();
        });
        confirmButton.setOnAction(event -> {
            if (!gameStarted) {
                new Thread(() -> {
                    try {
                        gameStarted = true;
                        Shake.XShake(confirmButton, 0f, -235f);
                        Thread.sleep(1500);
                        Shake.yShake(confirmButton, 0f, 180f);
                        Thread.sleep(1500);
                        Shake.XShake(confirmButton, -235f, 235f);
                        Thread.sleep(1500);
                        Shake.yShake(confirmButton, 180f, -180f);
                        Thread.sleep(1500);
                        gameStarted = false;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            } else {
                System.out.println("winner");
                testString = null;
                inputButton.getScene().getWindow().hide();
                MainFrame.confirmInputs.add("ok");
            }
        });

        errorsReceiver();
    }

    private void errorsReceiver() {
        new Thread(() -> {
            while (true) {
                String error;
                try {
                    error = CheckOutFrame.errors.take();
                    System.out.println(error);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    errorLabel.setText(PropertyWorker.getBundle().getString(error));
                });
            }

        }).start();
    }
}
