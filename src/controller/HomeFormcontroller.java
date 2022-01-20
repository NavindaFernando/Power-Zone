package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class HomeFormcontroller {

    public AnchorPane homeContext;
    public ImageView imageSeven;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXTextField txtWeight;
    public JFXTextField txtHeight;
    public Label lblBmi;
    public Label lblHealthyRange;
    public JFXTextField txtAge;
    public Label lblBmrMen;
    public JFXComboBox<String> cmbactivityLevels;
    public Label lblCalories;
    public Label lblLProtein;
    public Label lblHProtein;
    public Label lblBuildingFat;
    public Label lblFatLoss;
    public Label lblFatLossCarbs;
    public Label lblBuildCarbs;
    public Label lblproteinCalories;
    public Label lblFatCalories;
    public Label lblCarbCalories;
    public Label lblLossproteinCalories;
    public Label lblLossFatCalories;
    public Label lblLossCarbCalories;
    public Label lblJoinedMembers;
    public JFXButton btnCalculate;
    public Label lblOrderCount;

    double calories;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern weightPattern = Pattern.compile("^[1-9][0-9]{0,2}$");
    Pattern heightPattern = Pattern.compile("^[0-2]{1}([.][0-9]{1})$");
    Pattern agePattern = Pattern.compile("^[1-9]{1}[0-9]{1}$");

    public void initialize(){
        try {

            lblOrderCount.setText(getCountOrder());
            lblJoinedMembers.setText(getCountMember());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnCalculate.setDisable(true);
        storeValidations();

        cmbactivityLevels.getItems().addAll(
                "Sedenetary (nothing)",
                "Light (1 to 3)",
                "Moderate (3 to 5)",
                "Active (everyday)"
        );

        cmbactivityLevels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (txtWeight.getText().isEmpty() || txtHeight.getText().isEmpty()){
                new Alert(Alert.AlertType.WARNING,"Please Enter Your Weight, Height & Age First!").show();
            }else {

                double weight = Float.parseFloat(txtWeight.getText());
                double height = Float.parseFloat(txtHeight.getText());
                double age = Float.parseFloat(txtAge.getText());

                // calorie & activity level
                if (newValue == "Sedenetary (nothing)") {
                    double bmrSedenetary = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age) * 1.2;
                    lblCalories.setText(String.format("%.1f", bmrSedenetary));
                    calories = bmrSedenetary;
                }else if (newValue == "Light (1 to 3)"){
                    double bmrLight = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age) * 1.375;
                    lblCalories.setText(String.format("%.1f", bmrLight));
                    calories = bmrLight;
                }else if (newValue == "Moderate (3 to 5)") {
                    double bmrModerate = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age) * 1.55;
                    lblCalories.setText(String.format("%.1f", bmrModerate));
                    calories = bmrModerate;
                }else if (newValue == "Active (everyday)") {
                    double bmrActive = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age) * 1.725;
                    lblCalories.setText(String.format("%.1f", bmrActive));
                    calories = bmrActive;
                }
            }
        });
    }

    // calculation
    public void calculation(ActionEvent actionEvent) {

        if (txtWeight.getText().isEmpty() || txtHeight.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Enter Your Details!").show();
        }else {


            double weight = Float.parseFloat(txtWeight.getText());
            double height = Float.parseFloat(txtHeight.getText());
            double age = Float.parseFloat(txtAge.getText());

            // bmi
            double bmi = weight/(height*height);

            // bmr
            double bmrMen = 66+(13.7*weight)+(5*height*100)-(6.8*age);

            // protien
            double lowProtien = weight*1.6;
            double highProtien = weight*2.5;

            // fat
            double buildFat = calories*0.3/9;
            double lossFat = calories*0.25/9;

            // mussel building calorie count
            double proteinCalorie = lowProtien*4;
            double fatCalorie = calories*0.3;
            double totalFatAndProtein = proteinCalorie+fatCalorie;
            double carbCalorie = calories-totalFatAndProtein;

            // fat loss calorie count
            double lossProtienCalorie = highProtien*4;
            double lossFatCalorie = calories*0.25;
            double totalLossFatAndProtein = lossProtienCalorie+lossFatCalorie;
            double lossCarbCalorie = calories-totalLossFatAndProtein;

            // carb
            double buildCarbs = carbCalorie/4;
            double lossCarbs = lossCarbCalorie/4;

            lblBmi.setText(String.format("%.1f", bmi));
            lblBmrMen.setText(String.format("%.1f", bmrMen));
            lblLProtein.setText(String.format("%.1f", lowProtien));
            lblHProtein.setText(String.format("%.1f", highProtien));
            lblBuildingFat.setText(String.format("%.1f", buildFat));
            lblFatLoss.setText(String.format("%.1f", lossFat));
            lblproteinCalories.setText(String.format("%.1f", proteinCalorie));
            lblBuildCarbs.setText(String.format("%.1f", buildCarbs));
            lblFatCalories.setText(String.format("%.1f", fatCalorie));
            lblCarbCalories.setText(String.format("%.1f", carbCalorie));
            lblLossproteinCalories.setText(String.format("%.1f", lossProtienCalorie));
            lblLossFatCalories.setText(String.format("%.1f", lossFatCalorie));
            lblLossCarbCalories.setText(String.format("%.1f", lossCarbCalorie));
            lblFatLossCarbs.setText(String.format("%.1f", lossCarbs));

            // healthy range
            if (bmi <= 18.5){
                lblHealthyRange.setText("Underweight");
            }else if (bmi <= 24.9){
                lblHealthyRange.setText("Normal");
            }else if (bmi <= 29.9){
                lblHealthyRange.setText("Overweight");
            }else {
                lblHealthyRange.setText("Obesity");
            }
        }
    }

    // store validation
    private void storeValidations() {
        map.put(txtWeight, weightPattern);
        map.put(txtHeight, heightPattern);
        map.put(txtAge, agePattern);
    }

    // textFields_Key_Released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate2(map, btnCalculate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Details are correct!").showAndWait();
            }
        }
    }

    // member count
    public String getCountMember() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(id) FROM member ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // get count order
    public String getCountOrder() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(orderId) FROM `order` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // hide image
    public void hideImageSeven(MouseEvent mouseEvent) {
        imageSeven.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // open image
    public void openImageSeven(MouseEvent mouseEvent) {
        imageSeven.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // text clear
    public void clearText(MouseEvent mouseEvent) {
        txtWeight.clear();
        txtHeight.clear();
        txtAge.clear();
        lblBmi.setText("00.0");
        lblCalories.setText("0000.0");
        lblBmrMen.setText("0000.0");
        lblBuildCarbs.setText("000.0");
        lblLProtein.setText("000.0");
        lblFatLossCarbs.setText("000.0");
        lblHProtein.setText("000.0");
        lblBuildingFat.setText("0.0");
        lblFatLoss.setText("0.0");
        lblHealthyRange.setText("Normal");
        lblproteinCalories.setText("000.0");
        lblFatCalories.setText("000.0");
        lblCarbCalories.setText("000.0");
        lblLossproteinCalories.setText("000.0");
        lblLossFatCalories.setText("000.0");
        lblLossCarbCalories.setText("000.0");
        cmbactivityLevels.getSelectionModel().clearSelection();
    }

}