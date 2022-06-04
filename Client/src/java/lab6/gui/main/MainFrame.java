package lab6.gui.main;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lab6.client.commands.Utils;
import lab6.common.Coordinates;
import lab6.common.Person;
import lab6.common.Position;
import lab6.common.Worker;
import lab6.gui.AuthFrame;
import lab6.gui.Controller;
import lab6.gui.PropertyWorker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class MainFrame {

    @FXML
    private TitledPane addBumPane;

    @FXML
    private Button addWorkerButton;

    @FXML
    private TableColumn<Worker, String> birthdayColumn;

    @FXML
    private TextField birthdayField;


    @FXML
    private TableColumn<Worker, String> creationDateColumn;

    @FXML
    private TableColumn<Worker, String> endDateColumn;

    @FXML
    private TextField endDateField;

    @FXML
    private Label errorLabel;

    @FXML
    private Tab graphTab;

    @FXML
    private TableColumn<Worker, String> heightColumn;

    @FXML
    private TextField heightField;

    @FXML
    private TableColumn<Worker, String> idColumn;

    @FXML
    private Tab mainTab;

    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<Worker, String> personColumn;


    @FXML
    private ChoiceBox<String> positionChoice;

    @FXML
    private TableColumn<Worker, Enum> positionColumn;

    @FXML
    private Button quitButton;

    @FXML
    private TableColumn<Worker, String> salaryColumn;

    @FXML
    private TextField salaryField;


    @FXML
    private TableColumn<Worker, String> workerColumn;

    @FXML
    private TitledPane addWorkerPane;

    @FXML
    private ChoiceBox<String> selectLanguage;

    @FXML
    private TableColumn<Worker, String> startDateColumn;

    @FXML
    private TextField startDateField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Worker, String> userColumn;

    @FXML
    private TableColumn<Worker, String> weightColumn;

    @FXML
    private TextField weightField;

    @FXML
    private Label wrongPass;

    @FXML
    private TableColumn<Worker, String> xColumn;

    @FXML
    private TextField xField;

    @FXML
    private TableColumn<Worker, ?> coordinatesColumn;

    @FXML
    private TableColumn<Worker, String> yColumn;

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
        selectLanguage.setValue("ru");
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
        selectLanguage.setOnAction(event -> {
            localization(selectLanguage.getValue());
        });
        initTable();
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

    private void initTable(){
        localization(PropertyWorker.getLanguage());
//        table = new TableView<>();
//        workerColumn = new TableColumn<>("Worker");
//        personColumn = new TableColumn<>("person");
//        coordinatesColumn = new TableColumn<>("coords");
//        userColumn=new TableColumn<>("user");
//        idColumn = new TableColumn<>("id");
//        nameColumn = new TableColumn<>("name");
//        xColumn = new TableColumn<>("X");
//        yColumn = new TableColumn<>("Y");
//        creationDateColumn = new TableColumn<>("craDate");
//        salaryColumn = new TableColumn<>("salary");
//        startDateColumn = new TableColumn<>("Sdate");
//        endDateColumn = new TableColumn<>("eDate");
//        birthdayColumn = new TableColumn<>("birthdaay");
//        weightColumn = new TableColumn<>("weight");
//        heightColumn = new TableColumn<>("height");
//        positionColumn = new TableColumn<>("position");
//        coordinatesColumn.getColumns().addAll(xColumn, yColumn);
//        personColumn.getColumns().addAll(birthdayColumn, weightColumn, heightColumn);
//        table.getColumns().addAll(userColumn, idColumn, nameColumn, coordinatesColumn, creationDateColumn, salaryColumn, startDateColumn, endDateColumn, personColumn, positionColumn);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        yColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoordinates().getY().toString()));
        xColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoordinates().getX().toString()));
        creationDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getCreationDate())));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        startDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getStartDate())));
        endDateColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(dateFormat.format(cellDate.getValue().getEndDate())));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        birthdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(Date.from(cellData.getValue().getPerson().getBirthday().toInstant()))));
        heightColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getHeight().toString()));
        weightColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getWeight().toString()));


        Worker worker = new Worker("me");
        worker.setId(123);
        Coordinates coordinates = new Coordinates();
        coordinates.setXY(1, 2);
        Date date = new Date();
        Person person = new Person();
        person.setWeight((float) 123.1);
        person.setHeight((float) 44.1);
        LocalDateTime myLocalDateTime = LocalDateTime.of(2020, Month.MAY, 15, 13, 45, 30);
        ZoneId vnZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime vnDateTime2 = myLocalDateTime.atZone(vnZoneId);
        person.setBirthday(vnDateTime2);
        worker.setStats("jopa", coordinates, 123, date, date, Position.BAKER,person );
        System.out.println(worker);
        addWorkerToTable(worker);

        Worker worker1 = new Worker("me");
        worker1.setId(1323);
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setXY(2, 3);
        Date date1 = new Date(21313);
        Person person1 = new Person();
        person1.setWeight((float) 1234.1);
        person1.setHeight((float) 344.1);
        LocalDateTime myLocalDateTime1 = LocalDateTime.of(2030, Month.MAY, 15, 13, 45, 30);
        ZoneId vnZoneId1 = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime vnDateTime1 = myLocalDateTime1.atZone(vnZoneId1);
        person1.setBirthday(vnDateTime1);
        worker1.setStats("1jopa", coordinates1, 1233, date1, date1, Position.DIRECTOR,person1 );
        System.out.println(worker1);
        addWorkerToTable(worker1);
    }

    private void addWorkerToTable(Worker bum){

        table.getItems().addAll(bum);
    }

    public void localization(String language) {
        PropertyWorker.setNewBundle(language);
        userColumn.setText(PropertyWorker.getBundle().getString("user"));
        idColumn.setText(PropertyWorker.getBundle().getString("id"));
        nameColumn.setText(PropertyWorker.getBundle().getString("name"));
        coordinatesColumn.setText(PropertyWorker.getBundle().getString("coordinates"));
        creationDateColumn.setText(PropertyWorker.getBundle().getString("creationDate").replace(" ","\r"));
        salaryColumn.setText(PropertyWorker.getBundle().getString("salary"));
        startDateColumn.setText(PropertyWorker.getBundle().getString("startDate").replace(" ","\r"));
        endDateColumn.setText(PropertyWorker.getBundle().getString("endDate").replace(" ","\r"));
        positionColumn.setText(PropertyWorker.getBundle().getString("position"));
        birthdayColumn.setText(PropertyWorker.getBundle().getString("birthday"));
        heightColumn.setText(PropertyWorker.getBundle().getString("height"));
        weightColumn.setText(PropertyWorker.getBundle().getString("weight"));
        selectLanguage.setValue(PropertyWorker.getLanguage());
        mainTab.setText(PropertyWorker.getBundle().getString("mainTab"));
        graphTab.setText(PropertyWorker.getBundle().getString("graphTab"));
        nameField.setPromptText(PropertyWorker.getBundle().getString("name"));
        salaryField.setPromptText(PropertyWorker.getBundle().getString("salary"));
        startDateField.setPromptText(PropertyWorker.getBundle().getString("startDate"));
        endDateField.setPromptText(PropertyWorker.getBundle().getString("endDate"));
        birthdayField.setPromptText(PropertyWorker.getBundle().getString("birthday"));
        heightField.setPromptText(PropertyWorker.getBundle().getString("height"));
        weightField.setPromptText(PropertyWorker.getBundle().getString("weight"));
        addWorkerPane.setText(PropertyWorker.getBundle().getString("addWorker"));
        addWorkerButton.setText(PropertyWorker.getBundle().getString("addWorker"));
        quitButton.setText(PropertyWorker.getBundle().getString("logOut"));
        workerColumn.setText(PropertyWorker.getBundle().getString("workers"));
        personColumn.setText(PropertyWorker.getBundle().getString("person"));
        errorLabel.setText("");
        System.out.println(PropertyWorker.getBundle().getString("titleAuth"));

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
                    errorLabel.setText(PropertyWorker.getBundle().getString(response));
                });
            }

        }).start();
    }
}