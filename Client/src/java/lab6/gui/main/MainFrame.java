package lab6.gui.main;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lab6.client.commands.Utils;
import lab6.common.Worker;
import lab6.gui.AuthFrame;
import lab6.gui.Controller;
import lab6.gui.PropertyWorker;


import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MainFrame {

    @FXML
    private Button addIfMinButton;
    @FXML
    private TitledPane addWorkerPane;

    @FXML
    private Button addWorkerButton;


    @FXML
    private Tab addWorkerTab;

    @FXML
    private TableColumn<Worker, String> birthdayColumn;

    @FXML
    private TextField birthdayField;
    @FXML
    private TextField birthdayField1;


    @FXML
    private TableColumn<Worker, String> creationDateColumn;

    @FXML
    private TableColumn<Worker, String> endDateColumn;

    @FXML
    private TextField endDateField;


    @FXML
    private TextField endDateField1;


    @FXML
    private FlowPane errorFlow;

    @FXML
    private Tab graphTab;

    @FXML
    private TableColumn<Worker, Float> heightColumn;

    @FXML
    private Button executeScriptButton;


    @FXML
    private Button filterBySalaryButton;


    @FXML
    private Button infoButton;

    @FXML
    private Button helpButton;


    @FXML
    private Button historyButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField heightField1;

    @FXML
    private TableColumn<Worker, Integer> idColumn;

    @FXML
    private Tab mainTab;

    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nameField1;

    @FXML
    private TableColumn<Worker, String> personColumn;


    @FXML
    private ChoiceBox<String> positionChoice;

    @FXML
    private ChoiceBox<String> positionChoice1;

    @FXML
    private TableColumn<Worker, Enum> positionColumn;

    @FXML
    private Button quitButton;

    @FXML
    private Button reloadButton;

    @FXML
    private Button clearInfoButton;

    @FXML
    private TableColumn<Worker, String> salaryColumn;

    @FXML
    private TextField salaryField;


    @FXML
    private TextField salaryField1;


    @FXML
    private TableColumn<Worker, String> workerColumn;


    @FXML
    private ChoiceBox<String> selectLanguage;

    @FXML
    private TableColumn<Worker, String> startDateColumn;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField startDateField1;

    @FXML
    private TableView table;

    @FXML
    private Button updateWorkerButton;

    @FXML
    private Button removeByEndDate;

    @FXML
    private Button removeByIdButton;

    @FXML
    private Button removeLower;

    @FXML
    private Button clearButton;

    @FXML
    private Button printEndDatesButton;

    @FXML
    private Tab updateWorkerTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<Worker, String> userColumn;

    @FXML
    private TableColumn<Worker, Float> weightColumn;

    @FXML
    private TextField weightField;

    @FXML
    private TextField weightField1;

    @FXML
    private Label wrongPass;


    @FXML
    private TextFlow infoFlow;

    @FXML
    private Tab infoTab;

    @FXML
    private TableColumn<Worker, Long> xColumn;

    @FXML
    private TextField xField;
    @FXML
    private TextField xField1;

    @FXML
    private TableColumn<Worker, Integer> coordinatesColumn;

    @FXML
    private TableColumn<Worker, Integer> yColumn;

    @FXML
    private TextField yField;

    @FXML
    private TextField yField1;

    public static boolean showBoolean;

    Stage mainStage;

    private String curError;
    private final String[] languages = {"ru","eng", "lt", "et"};

    private final String[] positions = {"BAKER", "ENGINEER", "LABORER", "MANAGER", "DIRECTOR"};

    public static BlockingDeque<String> errors = null;
    public static BlockingDeque<List<Worker>> shows = null;

    public static BlockingDeque<String> responses = null;

    public static BlockingDeque<Float> floatInputs = null;

    public static BlockingDeque<Integer> integersInputs = null;

    public static BlockingDeque<Date> dateInputs = null;

    public static BlockingDeque<String> confirmInputs = null;

    public static BlockingDeque<Boolean> booleanInputs = null;


    public void start() {
        mainStage = Controller.openWindow("../gui/assets/mainApp.fxml");
        mainStage.show();
    }

    @FXML
    void initialize() {
        FileChooser fileChooser = new FileChooser();

        positionChoice.getItems().addAll(positions);
        positionChoice1.getItems().addAll(positions);
        errors = new LinkedBlockingDeque<>();
        shows = new LinkedBlockingDeque<>();
        responses = new LinkedBlockingDeque<>();
        floatInputs = new LinkedBlockingDeque<>();
        integersInputs = new LinkedBlockingDeque<>();
        dateInputs = new LinkedBlockingDeque<>();
        confirmInputs = new LinkedBlockingDeque<>();
        booleanInputs = new LinkedBlockingDeque<>();
        selectLanguage.setValue("ru");
        selectLanguage.getItems().addAll(languages);
        //selectLanguage.set
        quitButton.setOnAction(event -> {
            quitButton.getScene().getWindow().hide();
            AuthFrame authFrame = new AuthFrame();
            authFrame.start();
        });


        infoTab.setOnSelectionChanged(event -> {
            reloadButton.setVisible(false);
            clearInfoButton.setVisible(true);
        });
        graphTab.setOnSelectionChanged(event -> {
            reloadButton.setVisible(false);
            clearInfoButton.setVisible(false);
        });
        mainTab.setOnSelectionChanged(event -> {
            reloadButton.setVisible(true);
            clearInfoButton.setVisible(false);
        });
        addWorkerButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            blockButtons();
            System.out.println(("add " + nameField.getText() + " " + xField.getText() + " " + yField.getText() + " " + salaryField.getText() + " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue()));
            Utils.runCommandFromString("add " + nameField.getText() + " " + xField.getText() + " " + yField.getText() + " " + salaryField.getText() + " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue());
            enableButtons();
        });
        addIfMinButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            blockButtons();
            System.out.println(("add_If_min " + nameField.getText() + " " + xField.getText() + " " + yField.getText() + " " + salaryField.getText() + " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue()));
            Utils.runCommandFromString("add_If_min " + nameField.getText() + " " + xField.getText() + " " + yField.getText() + " " + salaryField.getText() + " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue());
            enableButtons();
        });

        infoButton.setOnAction(event -> {
            Utils.runCommandFromString("info");
        });
        updateWorkerButton.setOnAction(event -> {
            blockButtons();
            errorFlow.getChildren().clear();
            curError = null;
            System.out.println("updateid " + nameField.getText() + " " + xField.getText() + " " + yField.getText() + " " + salaryField.getText() + " " + startDateField.getText() + " " + endDateField.getText() + " " + birthdayField.getText() + " " + heightField.getText() + " " + weightField.getText() + " " + positionChoice.getValue() + " " + idField.getText());
            Utils.runCommandFromString("updateid " + nameField1.getText() + " " + xField1.getText() + " " + yField1.getText() + " " + salaryField1.getText() + " " + startDateField1.getText() + " " + endDateField1.getText() + " " + birthdayField1.getText() + " " + heightField1.getText() + " " + weightField1.getText() + " " + positionChoice1.getValue() + " " + idField.getText());
            enableButtons();
        });
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });
        reloadButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            showBoolean = false;
            nameColumn.setSortType(TableColumn.SortType.ASCENDING);
            TableColumn<Worker, ?> tableColumn;
            if (table.getSortOrder() != null && table.getSortOrder().size() > 0) {
                tableColumn = (TableColumn<Worker, ?>) table.getSortOrder().get(0);
                tableColumn.setSortType(null);
                table.getSortOrder().add(nameColumn);
                table.sort();
                table.getSortOrder().add(tableColumn);
                table.sort();

            }
            Utils.runCommandFromString("show");

        });
        clearInfoButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            infoFlow.getChildren().clear();
        });

        nameField.setOnMouseEntered(event -> {
            nameField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("name") + " : String"));
        });
        salaryField.setOnMouseEntered(event -> {
            salaryField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("salary") + " : Float"));
        });
        xField.setOnMouseEntered(event -> {
            xField.setTooltip(new Tooltip("X: Long"));
        });
        yField.setOnMouseEntered(event -> {
            yField.setTooltip(new Tooltip("Y: Integer"));
        });
        startDateField.setOnMouseEntered(event -> {
            startDateField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("startDate") + " : Date('DD.MM.YYYY')"));
        });
        endDateField.setOnMouseEntered(event -> {
            endDateField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("endDate") + " : Date('DD.MM.YYYY')"));
        });
        birthdayField.setOnMouseEntered(event -> {
            birthdayField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("birthday") + " : Date('DD.MM.YYYY')"));
        });
        weightField.setOnMouseEntered(event -> {
            weightField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("weight") + " : Float"));
        });
        heightField.setOnMouseEntered(event -> {
            heightField.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("height") + " : Float"));
        });
        nameField1.setOnMouseEntered(event -> {
            nameField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("name") + " : String"));
        });

        xField1.setOnMouseEntered(event -> {
            xField1.setTooltip(new Tooltip("X: Long"));
        });
        salaryField1.setOnMouseEntered(event -> {
            salaryField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("salary") + " : Float"));
        });
        yField1.setOnMouseEntered(event -> {
            yField1.setTooltip(new Tooltip("Y: Integer"));
        });
        endDateField1.setOnMouseEntered(event -> {
            endDateField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("endDate") + " : Date('DD.MM.YYYY')"));
        });
        birthdayField1.setOnMouseEntered(event -> {
            birthdayField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("birthday") + " : Date('DD.MM.YYYY')"));
        });
        startDateField1.setOnMouseEntered(event -> {
            startDateField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("startDate") + " : Date('DD.MM.YYYY')"));
        });
        weightField1.setOnMouseEntered(event -> {
            weightField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("weight") + " : Float"));
        });
        heightField1.setOnMouseEntered(event -> {
            heightField1.setTooltip(new Tooltip(PropertyWorker.getBundle().getString("height") + " : Float"));
        });
        idField.setOnMouseEntered(event -> {
            idField.setTooltip(new Tooltip("Id  : Integer"));
        });


        clearInfoButton.setOnMouseEntered(event -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText("help");
            clearInfoButton.setText("Hello");
            clearInfoButton.setTooltip(tooltip);
        });
        clearInfoButton.setOnMouseExited(event -> {
            clearInfoButton.setText(PropertyWorker.getBundle().getString("clearButton"));
        });
        printEndDatesButton.setOnAction(event -> {
            Utils.runCommandFromString("print_field_descending_end_date");
        });
        executeScriptButton.setOnAction(

                e -> {
                    errorFlow.getChildren().clear();
                    curError = null;
                    File file = fileChooser.showOpenDialog(mainStage);
                    if (file != null) {
                        new Thread(() -> {
                            blockButtons();
                            Utils.runCommandFromString("execute_script" + " " + file.getAbsolutePath());
                            enableButtons();
                        }).start();
                    }
                });
        helpButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            Utils.runCommandFromString("help");
        });
        filterBySalaryButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            showBoolean = true;
            new Thread(() -> {
                Platform.runLater(() -> {
                    blockButtons();
                    CheckOutFrame checkOutFrame = new CheckOutFrame();
                    checkOutFrame.checkOutFloat("salary");
                    enableButtons();
                });
            }).start();
            new Thread(() -> {
                try {
                    blockButtons();
                    Float input = floatInputs.take();
                    if (input != 0f) {
                        System.out.println("filter_by_salary " + input);
                        Utils.runCommandFromString("filter_by_salary " + input);
                        Platform.runLater(() -> {
                            errorFlow.getChildren().clear();
                            curError = null;
                        });
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    enableButtons();
                }
            }).start();
            tabPane.getSelectionModel().select(mainTab);
        });

        removeByIdButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            new Thread(() -> {
                Platform.runLater(() -> {
                    blockButtons();
                    CheckOutFrame checkOutFrame = new CheckOutFrame();
                    checkOutFrame.checkOutInteger("id");
                    enableButtons();
                });
            }).start();
            new Thread(() -> {
                try {
                    blockButtons();
                    Integer input = integersInputs.take();
                    System.out.println(input);
                    if (input != 0) {
                        System.out.println("remove by id " + input);
                        Utils.runCommandFromString("remove_by_id " + input);
                        Platform.runLater(() -> {
                            errorFlow.getChildren().clear();
                            curError = null;
                        });
                    } else {
                        Thread.interrupted();
                        Thread.currentThread().join();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    enableButtons();
                }
            }).start();
        });


        removeByEndDate.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            new Thread(() -> {
                Platform.runLater(() -> {
                    blockButtons();
                    CheckOutFrame checkOutFrame = new CheckOutFrame();
                    checkOutFrame.checkOutDate("date");
                    enableButtons();
                });
            }).start();
            new Thread(() -> {
                try {
                    blockButtons();
                    Date input = dateInputs.take();
                    if (!input.equals(new Date(0))) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        System.out.println("remove_all_by_end_date " + dateFormat.format(input));
                        Utils.runCommandFromString("remove_all_by_end_date " + dateFormat.format(input));
                        Platform.runLater(() -> {
                            errorFlow.getChildren().clear();
                            curError = null;
                        });
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    enableButtons();
                }
            }).start();
        });

        clearButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            new Thread(() -> {
                Platform.runLater(() -> {
                    blockButtons();
                    CheckOutFrame checkOutFrame = new CheckOutFrame();
                    checkOutFrame.checkOutString("clearConfirm");
                    enableButtons();
                });
            }).start();
            new Thread(() -> {
                try {
                    blockButtons();
                    String input = confirmInputs.take();
                    if (input.equals("ok")) {
                        Utils.runCommandFromString("clear");
                        System.out.println("clear ");
                    } else {
                        System.out.println("no please");
                    }
                    Platform.runLater(() -> {
                        errorFlow.getChildren().clear();
                        curError = null;
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    enableButtons();
                }
            }).start();
        });

        removeLower.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            new Thread(() -> {
                Platform.runLater(() -> {
                    blockButtons();
                    CheckOutFrame checkOutFrame = new CheckOutFrame();
                    checkOutFrame.checkOutBoolean("clearConfirm");
                    enableButtons();
                });
            }).start();
            new Thread(() -> {
                try {
                    blockButtons();
                    Boolean input = booleanInputs.take();
                    if (input) {
                        System.out.println("remove_lower");
                        Utils.runCommandFromString("remove_lower");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    enableButtons();
                }

            }).start();

        });

        historyButton.setOnAction(event -> {
            errorFlow.getChildren().clear();
            curError = null;
            Utils.runCommandFromString("history");
        });

        initTable();
        autoUpdate();
        errorsReceiver();
        responseReceiver();


    }

    private void autoUpdate() {
        new Thread(() -> {
            while (true) {
                Utils.runCommandFromString("show");
                try {
                    Thread.sleep(1000);
                    List<Worker> workers = shows.take();
                    TableColumn<Worker, ?> tableColumn;
                    if (table.getSortOrder() != null && table.getSortOrder().size() > 0) {
                        tableColumn = (TableColumn<Worker, ?>) table.getSortOrder().get(0);
                    } else {
                        tableColumn = null;
                    }
                    table.getItems().clear();
                    for (Worker i : workers) {
                        addWorkerToTable(i);
                    }
                    if (tableColumn != null) {
                        Platform.runLater(() -> {
                            table.getSortOrder().add(tableColumn);
                            table.sort();
                        });

                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    private void blockButtons() {
        executeScriptButton.setDisable(true);
        infoButton.setDisable(true);
        helpButton.setDisable(true);
        filterBySalaryButton.setDisable(true);
        historyButton.setDisable(true);
        reloadButton.setDisable(true);
        addWorkerButton.setDisable(true);
        addIfMinButton.setDisable(true);
        quitButton.setDisable(true);
        updateWorkerButton.setDisable(true);
        clearInfoButton.setDisable(true);
        removeByIdButton.setDisable(true);
        removeByEndDate.setDisable(true);
        removeLower.setDisable(true);
        clearButton.setDisable(true);
        printEndDatesButton.setDisable(true);
    }

    private void enableButtons() {
        executeScriptButton.setDisable(false);
        infoButton.setDisable(false);
        helpButton.setDisable(false);
        filterBySalaryButton.setDisable(false);
        historyButton.setDisable(false);
        reloadButton.setDisable(false);
        addWorkerButton.setDisable(false);
        addIfMinButton.setDisable(false);
        updateWorkerButton.setDisable(false);
        quitButton.setDisable(false);
        clearInfoButton.setDisable(false);
        removeByIdButton.setDisable(false);
        removeByEndDate.setDisable(false);
        removeLower.setDisable(false);
        clearButton.setDisable(false);
        printEndDatesButton.setDisable(false);
    }

    private void initTable() {
        localization(PropertyWorker.getLanguage());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        xColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getCoordinates().getY()));
        creationDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getCreationDate())));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        startDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getStartDate())));
        endDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getEndDate())));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        birthdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(Date.from(cellData.getValue().getPerson().getBirthday().toInstant()))));
        heightColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getPerson().getHeight()));
        weightColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getPerson().getWeight()));
    }

    private void addWorkerToTable(Worker bum) {
        table.getItems().addAll(bum);
    }


    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        userColumn.setText(PropertyWorker.getBundle().getString("user"));
        idColumn.setText(PropertyWorker.getBundle().getString("id"));
        nameColumn.setText(PropertyWorker.getBundle().getString("name"));
        coordinatesColumn.setText(PropertyWorker.getBundle().getString("coordinates"));
        creationDateColumn.setText(PropertyWorker.getBundle().getString("creationDate").replace(" ", "\r"));
        salaryColumn.setText(PropertyWorker.getBundle().getString("salary"));
        startDateColumn.setText(PropertyWorker.getBundle().getString("startDate").replace(" ", "\r"));
        endDateColumn.setText(PropertyWorker.getBundle().getString("endDate").replace(" ", "\r"));
        positionColumn.setText(PropertyWorker.getBundle().getString("position"));
        birthdayColumn.setText(PropertyWorker.getBundle().getString("birthday").replace(" ", "\r"));
        heightColumn.setText(PropertyWorker.getBundle().getString("height"));
        weightColumn.setText(PropertyWorker.getBundle().getString("weight"));
        selectLanguage.setValue(PropertyWorker.getLanguage());
        addWorkerPane.setText(PropertyWorker.getBundle().getString("workerTiltedPane"));
        addWorkerTab.setText(PropertyWorker.getBundle().getString("addWorkerTab"));
        updateWorkerTab.setText(PropertyWorker.getBundle().getString("updateWorkerTab"));
        mainTab.setText(PropertyWorker.getBundle().getString("mainTab"));
        graphTab.setText(PropertyWorker.getBundle().getString("graphTab"));
        infoTab.setText(PropertyWorker.getBundle().getString("infoButton"));

        nameField.setPromptText(PropertyWorker.getBundle().getString("name"));
        salaryField.setPromptText(PropertyWorker.getBundle().getString("salary"));
        startDateField.setPromptText(PropertyWorker.getBundle().getString("startDate"));
        endDateField.setPromptText(PropertyWorker.getBundle().getString("endDate"));
        birthdayField.setPromptText(PropertyWorker.getBundle().getString("birthday"));
        heightField.setPromptText(PropertyWorker.getBundle().getString("height"));
        weightField.setPromptText(PropertyWorker.getBundle().getString("weight"));
        nameField1.setPromptText(PropertyWorker.getBundle().getString("name"));
        salaryField1.setPromptText(PropertyWorker.getBundle().getString("salary"));
        endDateField1.setPromptText(PropertyWorker.getBundle().getString("endDate"));
        birthdayField1.setPromptText(PropertyWorker.getBundle().getString("birthday"));
        heightField1.setPromptText(PropertyWorker.getBundle().getString("height"));
        weightField1.setPromptText(PropertyWorker.getBundle().getString("weight"));
        startDateField1.setPromptText(PropertyWorker.getBundle().getString("startDate"));
        addWorkerButton.setText(PropertyWorker.getBundle().getString("addWorker"));
        updateWorkerButton.setText(PropertyWorker.getBundle().getString("updateWorkerTab"));
        addIfMinButton.setText(PropertyWorker.getBundle().getString("addIfMin"));
        quitButton.setText(PropertyWorker.getBundle().getString("logOut"));
        historyButton.setText(PropertyWorker.getBundle().getString("history"));
        workerColumn.setText(PropertyWorker.getBundle().getString("workers"));
        personColumn.setText(PropertyWorker.getBundle().getString("person"));
        executeScriptButton.setText(PropertyWorker.getBundle().getString("execScriptButton"));
        infoButton.setText(PropertyWorker.getBundle().getString("infoButton"));
        helpButton.setText(PropertyWorker.getBundle().getString("helpButton"));
        filterBySalaryButton.setText(PropertyWorker.getBundle().getString("filterBySalaryButton"));
        clearInfoButton.setText(PropertyWorker.getBundle().getString("clearButton"));
        removeByIdButton.setText(PropertyWorker.getBundle().getString("removeById"));
        removeByEndDate.setText(PropertyWorker.getBundle().getString("removeByEndDate"));
        removeLower.setText(PropertyWorker.getBundle().getString("removeLower"));
        clearButton.setText(PropertyWorker.getBundle().getString("clearCollection"));
        printEndDatesButton.setText(PropertyWorker.getBundle().getString("PrintAllEndDates"));
        if (curError != null) {
            errorFlow.getChildren().clear();
            Text text = Controller.getErrorText1();
            text.setText(PropertyWorker.getBundle().getString(curError));
            errorFlow.getChildren().add(text);
        }
        System.out.println(PropertyWorker.getBundle().getString("titleAuth"));

    }

    private void errorsReceiver() {
        new Thread(() -> {
            while (true) {
                String response;
                try {
                    response = MainFrame.errors.take();
                    curError = response;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Text text = Controller.getErrorText1();
                text.setText(PropertyWorker.getBundle().getString(response));
                Platform.runLater(() -> {
                    errorFlow.getChildren().clear();
                    errorFlow.getChildren().add(text);
                });
            }

        }).start();
    }

    private void responseReceiver() {
        new Thread(() -> {
            while (true) {
                String response;
                try {
                    response = MainFrame.responses.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(() -> {

                    try {
                        Text text = new Text(PropertyWorker.getBundle().getString(response).replace("+", "\r\n"));
                        infoFlow.getChildren().add(text);
                        infoFlow.getChildren().add(new Text(" "));
                        // infoFlow.getChildren().add(new Text("\r\n"));
                    } catch (Exception e) {
                        Text text = new Text(response);
                        infoFlow.getChildren().add(text);
                        infoFlow.getChildren().add(new Text(" "));
                        //infoFlow.getChildren().add(new Text("\r\n"));
                    }
                    tabPane.getSelectionModel().select(infoTab);
                });
            }

        }).start();
    }
}