package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.MemberTM;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddMemberFormcontroller extends MembersFormcontroller {

    public ImageView imageThree;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXButton btnAdd;
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtGender;
    public TextField txtAddress;
    public TextField txtDateOfBirth;
    public TextField txtOccupation;
    public TextField txtAge;
    public TextField txtWeight;
    public TextField txtHeight;
    public JFXComboBox cmbBodyType;
    public JFXComboBox cmbTranningType;
    public JFXComboBox cmbTranningHistory;
    public JFXComboBox cmbActivityLevel;
    public JFXComboBox cmbCurrentDiet;
    public FontAwesomeIconView lblBodyDetailHide;
    public FontAwesomeIconView lblBodyDetailOpen;
    public JFXButton btnPrint;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[M][0-9]{3,}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern contactPattern = Pattern.compile("^(077|071|078|075)[-]?[0-9]{7}$");
    Pattern genderPattern = Pattern.compile("^(male)|(female)$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{4,100}$");
    Pattern birthdayPattern = Pattern.compile("^[1-2][0,9][0-9]{2}[-][0-9]{2}[-][0-9]{2}$");
    Pattern occupationPattern = Pattern.compile("^[A-z0-9/ ]{2,25}$");
    Pattern agePattern = Pattern.compile("^[1-9]{1}[0-9]{1}$");
    Pattern weightPattern = Pattern.compile("^[1-9][0-9]{0,2}$");
    Pattern heightPattern = Pattern.compile("^[0-2]{1}([.][0-9]{1})$");

    public void initialize() {
        btnAdd.setDisable(true);
        storeValidations();

        cmbBodyType.getItems().addAll(
                "ectomorph","mesomorph","endomorph"
        );

        cmbTranningHistory.getItems().addAll(
          "No Exercises","Less Than 1-2 Years","2-5 Years","5+ Years"
        );

        cmbTranningType.getItems().addAll(
          "Bodybuilding","Powerlifting","Powerbuilding","Crossfit","None of the above"
        );

        cmbActivityLevel.getItems().addAll(
                "Sedentary","Lightly Active","Active","Very Active"
        );

        cmbCurrentDiet.getItems().addAll(
                "No Diet Plan","Balanced Diet","Low Carb","Vegetarian","Keto/Paleo","Vegan"
        );
    }

    // save member
    public void saveMember(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Member m1 = new Member(
                txtId.getText(), txtName.getText(), txtContact.getText(), txtGender.getText(), txtAddress.getText(), txtDateOfBirth.getText(), txtOccupation.getText());

        if (new MemberControler().saveMember(m1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search member
    public void searchMembers(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String memberId = txtId.getText();

        Member m1 = new MemberControler().getMember(memberId);
        if (m1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(m1);
        }
    }

    void setData(Member m) {
        txtId.setText(m.getId());
        txtName.setText(m.getName());
        txtContact.setText(m.getContact());
        txtGender.setText(m.getGender());
        txtAddress.setText(m.getAddress());
        txtDateOfBirth.setText(m.getBirthday());
        txtOccupation.setText(m.getOccupation());
    }

    // update member
    public void updateMember(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Member m1 = new Member(
                txtId.getText(), txtName.getText(), txtContact.getText(), txtGender.getText(), txtAddress.getText(), txtDateOfBirth.getText(), txtOccupation.getText());

        if (new MemberControler().updateMember(m1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete member
    public void deleteMember(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (new MemberControler().deleteMember(txtId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // store validation
    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtContact, contactPattern);
        map.put(txtGender, genderPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtDateOfBirth, birthdayPattern);
        map.put(txtOccupation, occupationPattern);
        map.put(txtAge, agePattern);
        map.put(txtWeight, weightPattern);
        map.put(txtHeight, heightPattern);
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
    public void hideImageThree(MouseEvent mouseEvent) {
        imageThree.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display image
    public void openImageThree(MouseEvent mouseEvent) {
        imageThree.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // clear text field
    public void clearTextField(MouseEvent mouseEvent) {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtGender.clear();
        txtAddress.clear();
        txtDateOfBirth.clear();
        txtOccupation.clear();
    }

    // member report
    public void printMember(ActionEvent actionEvent) {
        try {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/MemberReport.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);

        String name = txtName.getText();
        String id = txtId.getText();
        String age = txtAge.getText();
        String weight = txtWeight.getText();
        String height = txtHeight.getText();
        Object bodyType = cmbBodyType.getValue();
        Object traningType = cmbTranningType.getValue();
        Object traningHistory = cmbTranningHistory.getValue();
        Object activityLevel = cmbActivityLevel.getValue();
        Object currentDiet = cmbCurrentDiet.getValue();

        HashMap map = new HashMap();
        map.put("memberName", name);
        map.put("memberId", id);
        map.put("memberAge", age);
        map.put("memberWeight", weight);
        map.put("memberHeight", height);
        map.put("bodyType", bodyType);
        map.put("traningType", traningType);
        map.put("traningHistory",traningHistory);
        map.put("activityLevel", activityLevel);
        map.put("currentDiet", currentDiet);

        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
        JasperViewer.viewReport(jasperPrint, false);

    } catch (JRException e) {
        e.printStackTrace();
    }
    }

    // open body detail
    public void openBodyDetail(MouseEvent mouseEvent) {
        imageThree.setVisible(false);
        cmbBodyType.setVisible(true);
        cmbTranningType.setVisible(true);
        cmbTranningHistory.setVisible(true);
        cmbActivityLevel.setVisible(true);
        cmbCurrentDiet.setVisible(true);
        eyeHide.setVisible(false);
        lblBodyDetailOpen.setVisible(true);
        lblBodyDetailHide.setVisible(false);
    }

    // hide body detail
    public void hideBodyDetail(MouseEvent mouseEvent) {
        imageThree.setVisible(true);
        cmbBodyType.setVisible(false);
        cmbTranningType.setVisible(false);
        cmbTranningHistory.setVisible(false);
        cmbActivityLevel.setVisible(false);
        cmbCurrentDiet.setVisible(false);
        eyeHide.setVisible(true);
        lblBodyDetailOpen.setVisible(false);
        lblBodyDetailHide.setVisible(true);
    }
}