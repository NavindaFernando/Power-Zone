package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AdminDashBoardcontroller {
    public Label lblA;
    public Label lblB;
    public FontAwesomeIconView imgLock;
    public FontAwesomeIconView imgClose;
    Stage stage;
    public AnchorPane adminDashBoardFormContext;
    public Label lblTime;
    public Label lblDate;
    public AnchorPane adminContext;

    public void initialize() throws IOException {
        loadDateAndTime();
        loadUi("AdminHomeForm");
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy - MM - dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    // open login owner
    public void openLoginOwnerForm(MouseEvent mouseEvent) throws IOException {
        Stage window = (Stage) adminDashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormOwner.fxml"))));
    }

    // open login owner
    public void openLoginFormOwner(MouseEvent mouseEvent) throws IOException {
        Stage window = (Stage) adminDashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormOwner.fxml"))));
    }

    public void displayAdminDashBoard(ActionEvent actionEvent) throws IOException {
        loadUi("AdminHomeForm");
    }

    public void displayAddCoach(ActionEvent actionEvent) throws IOException {
        loadUi("AddCoachForm");
    }

    public void displayCoach(ActionEvent actionEvent) throws IOException {
        loadUi("CoachForm");
    }

    public void displayAddItem(ActionEvent actionEvent) throws IOException {
        loadUi("AddItemForm");
    }

    public void displayItems(ActionEvent actionEvent) throws IOException {
        loadUi("ItemForm");
    }

    public void displayCalculator(ActionEvent actionEvent) throws IOException {
        loadUi("TimeTable");
    }

    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load =FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    // log out
    public void logOut(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting ?");
        if(alert.showAndWait().get()== ButtonType.OK) {
            stage = (Stage) adminDashBoardFormContext.getScene().getWindow();
            stage.close();
        }
    }

    // color change (black)
    public void changeColorBlack(MouseEvent mouseEvent) {
        adminDashBoardFormContext.setStyle("-fx-background-color:  #1F2123");
        lblDate.setStyle("-fx-text-fill: #b5b5b5");
        lblTime.setStyle("-fx-text-fill: #b5b5b5");
        imgClose.setStyle("-fx-fill: #b5b5b5");
        imgLock.setStyle("-fx-fill: #b5b5b5");
        lblB.setStyle("-fx-text-fill: #b5b5b5");
        lblA.setStyle("-fx-text-fill: #b5b5b5");
    }

    // color change (white)
    public void chageColorWhite(MouseEvent mouseEvent) {
        adminDashBoardFormContext.setStyle("-fx-background-color: white");
        lblDate.setStyle("-fx-text-fill: #393E46");
        lblTime.setStyle("-fx-text-fill: #393E46");
        imgClose.setStyle("-fx-fill: #393E46");
        imgLock.setStyle("-fx-fill: #393E46");
        lblB.setStyle("-fx-text-fill: #393E46");
        lblA.setStyle("-fx-text-fill: #393E46");
    }
}
