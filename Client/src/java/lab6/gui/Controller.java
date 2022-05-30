package lab6.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab6.client.ClientRunner;

import java.io.IOException;

public class Controller {
   // private static
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
        return stage;

    }
}
