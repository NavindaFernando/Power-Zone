package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormCoachcontroller {

    public AnchorPane loginCoachContext;
    public JFXTextField txtUserName;

    public Label lblUserName;
    public JFXPasswordField txtPassword;
    public JFXCheckBox checkBox;

    // open owner login form
    public void openOwnerForm(MouseEvent actionEvent) throws IOException {
        Stage window = (Stage) loginCoachContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginFormOwner.fxml"))));
    }

    // checking coach login & open dashboard
    public void openDashBoardForm(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        Stage window = (Stage) loginCoachContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));

       /* String username = txtUserName.getText();
        String password = txtPassword.getText();

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from Coach where userName = ? and password = ?");

        preparedStatement.setObject(1,username);
        preparedStatement.setObject(2,password);

        ResultSet set = preparedStatement.executeQuery();

        boolean next = set.next();

        if (next){

            lblUserName.setVisible(false);

            Parent parent= FXMLLoader.load(this.getClass().getResource("../view/DashBoardForm.fxml"));
            Scene scene=new Scene(parent);
            Stage primaryStage=(Stage)this.loginCoachContext.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        }else {

            lblUserName.setVisible(true);

            txtUserName.clear();
            txtPassword.clear();
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
