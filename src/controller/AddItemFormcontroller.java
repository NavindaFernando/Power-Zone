package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Item;
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddItemFormcontroller extends ItemFormcontroller {

    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public ImageView imageTwo;
    public JFXTextField txtItemCode;
    public JFXTextField txtName;
    public JFXTextField txtDescription;
    public JFXTextField txtQryOnHand;
    public JFXTextField txtSize;
    public JFXTextField txtUnitPrice;
    public JFXButton btnAdd;
    public Label lblItemCounts;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern codePattern = Pattern.compile("^[I][0-9]{3,}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{2,50}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z0-9/ ]{2,100}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,5}$");
    Pattern sizePattern = Pattern.compile("^[A-z0-9/ ]{2,50}$");
    Pattern pricePattern = Pattern.compile("^[0-9/=.]{1,7}$");

    public void initialize(){
        try {
            lblItemCounts.setText(getCountItem());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnAdd.setDisable(true);
        storeValidations();
    }

    // save item
    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i1 = new Item(
                txtItemCode.getText(), txtName.getText(), txtDescription.getText(), Integer.parseInt(txtQryOnHand.getText()), txtSize.getText(), Double.parseDouble(txtUnitPrice.getText()));

        if (new ItemController().saveItem(i1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search item
    public void searchItem(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();

        Item i1 = new ItemController().getItem(itemCode);
        if (i1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(i1);
        }
    }

    void setData(Item i){
        txtItemCode.setText(i.getItemCode());
        txtName.setText(i.getName());
        txtDescription.setText(i.getDescription());
        txtQryOnHand.setText(String.valueOf(i.getQtyOnHand()));
        txtSize.setText(i.getSize());
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
    }

    // update item
    public void updateItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i1 = new Item(
                txtItemCode.getText(), txtName.getText(), txtDescription.getText(), Integer.parseInt(txtQryOnHand.getText()), txtSize.getText(), Double.parseDouble(txtUnitPrice.getText()));

        if (new ItemController().updateItem(i1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete item
    public void deleteItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ItemController().deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // store validation
    private void storeValidations() {
        map.put(txtItemCode, codePattern);
        map.put(txtName, namePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtQryOnHand, qtyPattern);
        map.put(txtSize, sizePattern);
        map.put(txtUnitPrice, pricePattern);
    }

    // textFields key released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate2(map, btnAdd);

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
    public void hideImagetwo(MouseEvent mouseEvent) {
        imageTwo.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display image
    public void openImageTwo(MouseEvent mouseEvent) {
        imageTwo.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // clear item text field
    public void clearTextField(MouseEvent mouseEvent) {
        txtItemCode.clear();
        txtName.clear();
        txtDescription.clear();
        txtQryOnHand.clear();
        txtSize.clear();
        txtUnitPrice.clear();
    }

    // item report
    public void openItemReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/Item_Report.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            String totalItem = lblItemCounts.getText();

            HashMap map = new HashMap();
            map.put("itemCount", totalItem);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
