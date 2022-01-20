package controller;

import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardFormcontroller {
    public FontAwesomeIconView imgTimetable;
    public FontAwesomeIconView imgLock;
    public FontAwesomeIconView imgClose;
    public JFXToggleButton btnColor;
    public Label lblB;
    public Label lblA;
    Stage stage;
    public AnchorPane dashBoardFormContext;
    public Label lblTime;
    public Label lblDate;
    public AnchorPane context;
    public Label lblAvatarName;

    public void initialize() throws IOException {
        loadDateAndTime();
        loadUi("HomeForm");
    }

    // date & time
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

    // open login coach
    public void openLoginFormCoach(MouseEvent mouseEvent) throws IOException {
        Stage window = (Stage) dashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormCoach.fxml"))));
    }

    // open login coach
    public void openLoginCoachForm(MouseEvent mouseEvent) throws IOException {
        Stage window = (Stage) dashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormCoach.fxml"))));
    }

    public void displayDashBoard(ActionEvent actionEvent) throws IOException {
        loadUi("HomeForm");
    }

    public void displayAddMember(ActionEvent actionEvent) throws IOException {
        loadUi("AddMemberForm");
    }

    public void displayMembers(ActionEvent actionEvent) throws IOException {
        loadUi("MembersForm");
    }

    public void displayAddOrder(ActionEvent actionEvent) throws IOException {
        loadUi("OrderForm");
    }

    public void displayPayment(ActionEvent actionEvent) throws IOException {
        loadUi("PaymentForm");
    }

    public void openCoachTimeTable(MouseEvent mouseEvent) throws IOException {
        loadUi("CoachTimeTableForm");
    }

    // load ui
    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load =FXMLLoader.load(resource);
        context.getChildren().clear();
        context.getChildren().add(load);
    }

    // log out
    public void logOut(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting ?");
        if(alert.showAndWait().get()== ButtonType.OK) {
            stage = (Stage) dashBoardFormContext.getScene().getWindow();
            stage.close();
        }
    }

    // color change (black)
    public void changeColorBlack(MouseEvent mouseEvent) {
        dashBoardFormContext.setStyle("-fx-background-color:  #1F2123");
        lblDate.setStyle("-fx-text-fill: #b5b5b5");
        lblTime.setStyle("-fx-text-fill: #b5b5b5");
        imgTimetable.setStyle("-fx-fill: #b5b5b5");
        imgClose.setStyle("-fx-fill: #b5b5b5");
        imgLock.setStyle("-fx-fill: #b5b5b5");
        lblB.setStyle("-fx-text-fill: #b5b5b5");
        lblA.setStyle("-fx-text-fill: #b5b5b5");
    }

    // color change (white)
    public void chageColorWhite(MouseEvent mouseEvent) {
        dashBoardFormContext.setStyle("-fx-background-color: white");
        lblDate.setStyle("-fx-text-fill: #393E46");
        lblTime.setStyle("-fx-text-fill: #393E46");
        imgTimetable.setStyle("-fx-fill: #393E46");
        imgClose.setStyle("-fx-fill: #393E46");
        imgLock.setStyle("-fx-fill: #393E46");
        lblB.setStyle("-fx-text-fill: #393E46");
        lblA.setStyle("-fx-text-fill: #393E46");
    }

    public void changeColorTransparent(MouseEvent mouseEvent) {
        dashBoardFormContext.setStyle("-fx-background-color: transparent");
    }
}
