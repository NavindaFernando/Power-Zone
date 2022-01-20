package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Coach;
import model.Feedback;
import model.Item;
import model.Member;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCoachFormcontroller {

    public ImageView imageOne;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXTextField txtDate;
    public JFXTextField txtMassage;
    public AnchorPane addCoachContext;
    public TextField txtCoachId;
    public TextField txtCoachName;
    public TextField txtCoachContact;
    public TextField txtCoachGender;
    public TextField txtCoachAddress;
    public TextField txtCoachAge;
    public TextField txtCoachUserName;
    public TextField txtCoachPassword;
    public JFXButton btnAdd;
    public Label lblCoach;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C][0-9]{3,}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern contactPattern = Pattern.compile("^(077|071|078|075)[-]?[0-9]{7}$");
    Pattern genderPattern = Pattern.compile("^(male)|(female)$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{4,100}$");
    Pattern agePattern = Pattern.compile("^[1-9]{1}[0-9]{1}$");
    Pattern userPattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9 ]{4,100}$");

    public void initialize(){

        try {
            lblCoach.setText(getCountCoach());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnAdd.setDisable(true);
        storeValidations();
        loadDate();
    }

    // save coach
    public void saveCoach(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Coach c1 = new Coach(
                txtCoachId.getText(), txtCoachName.getText(), txtCoachContact.getText(), txtCoachGender.getText(), txtCoachAddress.getText(), Integer.parseInt(txtCoachAge.getText()), txtCoachUserName.getText(), txtCoachPassword.getText());

        if (new CoachController().saveCoach(c1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search coach
    public void searchCoach(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String coachId = txtCoachId.getText();

        Coach c1 = new CoachController().getCoach(coachId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    void setData(Coach c) {
        txtCoachId.setText(c.getCoachId());
        txtCoachName.setText(c.getName());
        txtCoachContact.setText(c.getContact());
        txtCoachGender.setText(c.getGender());
        txtCoachAddress.setText(c.getAddress());
        txtCoachAge.setText(String.valueOf(c.getAge()));
        txtCoachUserName.setText(c.getUserName());
        txtCoachPassword.setText(c.getPassword());
    }

    // update coach
    public void updateCoach(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Coach c1 = new Coach(
                txtCoachId.getText(), txtCoachName.getText(), txtCoachContact.getText(), txtCoachGender.getText(), txtCoachAddress.getText(), Integer.parseInt(txtCoachAge.getText()), txtCoachUserName.getText(), txtCoachPassword.getText());

        if (new CoachController().updateCoach(c1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete coach
    public void deleteCoach(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (new CoachController().deleteCoach(txtCoachId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // save massage
    public void saveMassage(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Feedback f1 = new Feedback(
                txtDate.getText(), txtMassage.getText());

        if (new FeedbackController().saveFeedback(f1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search Massage
    public void searchMassage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String feedbackDate = txtDate.getText();

        Feedback f1 = new FeedbackController().getFeedback(feedbackDate);
        if (f1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(f1);
        }
    }

    void setData(Feedback f) {
        txtDate.setText(f.getDate());
        txtMassage.setText(f.getMassage());
    }

    // update massage
    public void selectDate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String massageDate = txtDate.getText();

        Feedback f1 = new FeedbackController().getFeedback(massageDate);
        if (f1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(f1);
        }
    }

    public void updateMassage(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Feedback f1 = new Feedback(
                txtDate.getText(), txtMassage.getText());

        if (new FeedbackController().updateFeedback(f1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete massage
    public void deleteMassage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (new FeedbackController().deleteFeedback(txtDate.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // load date
    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));
    }

    // coach count
    public String getCountCoach() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(coachId) FROM coach ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // store validation
    private void storeValidations() {
        map.put(txtCoachId, idPattern);
        map.put(txtCoachName, namePattern);
        map.put(txtCoachContact, contactPattern);
        map.put(txtCoachGender, genderPattern);
        map.put(txtCoachAddress, addressPattern);
        map.put(txtCoachAge, agePattern);
        map.put(txtCoachUserName, userPattern);
        map.put(txtCoachPassword, passwordPattern);
    }

    // textFields key released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Details are correct!").showAndWait();
            }
        }
    }

    // hide image
    public void hideImage(MouseEvent mouseEvent) {
        imageOne.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display image
    public void openImage(MouseEvent mouseEvent) {
        imageOne.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // clear coach text field
    public void clearCoachTextField(MouseEvent mouseEvent) {
        txtCoachId.clear();
        txtCoachName.clear();
        txtCoachAddress.clear();
        txtCoachAge.clear();
        txtCoachGender.clear();
        txtCoachContact.clear();
        txtCoachUserName.clear();
        txtCoachPassword.clear();
    }

    // clear massage
    public void clearMassage(MouseEvent mouseEvent) {
        txtMassage.clear();
    }

    // open admin payment form
    public void openAdminPaymentForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminPaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        addCoachContext.getChildren().clear();
        addCoachContext.getChildren().add(load);
    }
}
