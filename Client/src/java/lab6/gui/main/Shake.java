package lab6.gui.main;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;


    public static void Shake(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), node);
        translateTransition.setFromX(-10f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(5);
        translateTransition.setAutoReverse(true);
        translateTransition.playFromStart();

    }

    public static void XShake(Node node, double start, double end){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1500), node);
        translateTransition.setFromX(start);
        translateTransition.setByX(end);
        translateTransition.playFromStart();
    }

    public static void yShake (Node node,double start, double end){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1500), node);
        translateTransition.setFromY(start);
        translateTransition.setByY(end);
        translateTransition.playFromStart();
    }



    private void playShake(){
        tt.playFromStart();
    }
}
