package lab6.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lab6.client.ClientRunner;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Controller {

    public static Stage openWindow (String window){
        FXMLLoader fxmlLoader = new FXMLLoader(ClientRunner.class.getResource(window));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        File file = new File("C:\\Users\\pomat\\IdeaProjects\\lab8\\Client\\src\\java\\lab6\\gui\\assets\\logo.png");
        try {
            String localURl= file.toURI().toURL().toString();
            System.out.println(localURl);
            Image image = new Image(localURl);
            stage.getIcons().add(image);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Hafiz Mustafa");
        return stage;

    }
    public static Text getErrorText(){
        Text text = new Text(0, 0, "");
        Font font = Font.font("Campus", FontWeight.BOLD, FontPosture.REGULAR, 16);
        text.setFont(font);
        text.setFill(Color.RED);
        text.setStrokeWidth(0.55);
        text.setStroke(Color.BLACK);
        return text;
    }

    public static Text getErrorText1(){
        Text text = new Text(0, 0, "");
        Font font = Font.font("Campus", FontWeight.NORMAL, FontPosture.REGULAR, 12);
        text.setFont(font);
        text.setFill(Color.RED);
        text.setStrokeWidth(0.5);
        text.setStroke(Color.BLACK);
        return text;
    }

    public static Text getTitleText(){
        Text text = new Text(0, 0, PropertyWorker.getBundle().getString("titleReg"));
        Font font = Font.font("Campus", FontWeight.BOLD, FontPosture.REGULAR, 40);
        text.setFont(font);
        text.setFill(Color.ORANGE);
        text.setStrokeWidth(0.55);
        text.setStroke(Color.BLACK);
        return text;
    }


}
