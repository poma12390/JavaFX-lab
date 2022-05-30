package lab6.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab6.client.commands.Utils;
import lab6.gui.AuthFrame;
import lab6.gui.PropertyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.NoSuchElementException;

public class ClientRunner extends Application {
    AuthFrame authFrame = new AuthFrame();
    @Override
    public void start(Stage stage) throws Exception {
        authFrame.start();

    }
    private static final Logger logger
            = LoggerFactory.getLogger(ClientRunner.class);
    public static void main(String[] args) {
        new Thread(() -> {
            // process queue
            launch();
        }).start();


        PropertyWorker.init();
        InputStream inputStream = System.in;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Utils.setCurrentBufferedReader(bufferedReader);


        //String input = "help";
        //Utils.runCommandFromString(input);
        try {

            while (true) {
                System.out.print("Write a command: ");
                String input = bufferedReader.readLine();
                Utils.runCommandFromString(input);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            Utils.funExit();
        } catch (Exception e) {
            e.printStackTrace();
        }


//System.out.println(crd.getCommandArgs().getBum().getName() + " " + crd.getCommandName());

//        try {
//
//            serverCaller.sendToServer(transformer.Serialize(crd));}
//        catch (ServerNotFoundException e){
//            System.out.println(e.getMessage());
//        }


    }


}
