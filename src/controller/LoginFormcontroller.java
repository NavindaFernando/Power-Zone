package controller;

import com.jfoenix.controls.JFXProgressBar;
import com.sun.javafx.applet.Splash;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginFormcontroller {
    Stage stage;
    public AnchorPane startFormContext;
    public JFXProgressBar pgb;

    // progress bar
    public void initialize(){
        pgb.setProgress(0);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(120), event -> {
            if (pgb.getProgress() <= 1) {
                pgb.setProgress(pgb.getProgress() + 0.01);
            }
        }));
        tl.setRate(3);
        tl.setCycleCount(Animation.INDEFINITE);
        tl.playFromStart();
        pgb.progressProperty().addListener((observable, oldValue, newValue) -> {

            try {
                if (newValue.intValue() == 1) {
                    tl.stop();
                    Stage window = (Stage) startFormContext.getScene().getWindow();
                    window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormCoach.fxml"))));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    // log out
    public void logOut(MouseEvent mouseEvent) {
        stage = (Stage) startFormContext.getScene().getWindow();
        stage.close();
    }
}
