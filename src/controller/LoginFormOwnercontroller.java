package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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

import java.io.IOException;
import java.net.URL;

public class LoginFormOwnercontroller {

    public AnchorPane looginOwnerContext;
    public JFXTextField txtUsername;
    public Label lblUserName;
    public JFXPasswordField txtPassword;
    public JFXCheckBox checkBox;

    // open coach form
    public void openCoachForm(MouseEvent mouseEvent) throws IOException {
        Stage window = (Stage) looginOwnerContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormCoach.fxml"))));
    }

    // checking admin login & open dashboard
    public void openAdminDashBoardForm(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) looginOwnerContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoard.fxml"))));

       /* if (txtUsername.getText().equals("mns fernando") && txtPassword.getText().equals("2021")) {
            Stage window = (Stage) looginOwnerContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoard.fxml"))));

        } else {
            lblUserName.setVisible(true);
        }*/
    }

    // move to password
    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    // show password
    public void checkPassword(ActionEvent actionEvent) {

    }
}
