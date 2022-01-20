package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Coach;
import model.Payment;
import model.TimeTable;
import view.tm.PaymentTM;
import view.tm.TimeTableTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminPaymentFormController {

    public JFXButton btn0;
    public JFXButton btn1;
    public JFXButton btn2;
    public JFXButton btn3;
    public JFXButton btn4;
    public JFXButton btn5;
    public JFXButton btn6;
    public JFXButton btn7;
    public JFXButton btn8;
    public JFXButton btn9;
    public TextField txtAnswer;
    public Label label;
    public ImageView image;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXTextField txtPaymentName;
    public JFXTextField txtValidTime;
    public JFXTextField txtPrice;
    public TableView tblPayment;
    public TableColumn colPaymentName;
    public TableColumn colValidTime;
    public TableColumn colPrice;

    double num , answer;
    int calculation;

    public void initialize(){

        colPaymentName.setCellValueFactory(new PropertyValueFactory<>("paymentName"));
        colValidTime.setCellValueFactory(new PropertyValueFactory<>("validTime"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            setPaymentToTable(new PaymentController().getAllPayment());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // set payment to tabel
    private void setPaymentToTable(ArrayList<Payment> payments) {
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
        payments.forEach(e -> {
            obList.add(
                    new PaymentTM(e.getPaymentName(), e.getValidTime(), e.getPrice()));
        });
        tblPayment.setItems(obList);
    }

    // save payment
    public void savePayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment p1 = new Payment(
                txtPaymentName.getText(), txtValidTime.getText(), Double.parseDouble(txtPrice.getText()));

        if (new PaymentController().savePayment(p1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search payment
    public void searchPayment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String paymentId = txtPaymentName.getText();

        Payment p1 = new PaymentController().getPayment(paymentId);
        if (p1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(p1);
        }
    }

    void setData(Payment p) {
        txtPaymentName.setText(p.getPaymentName());
        txtValidTime.setText(p.getValidTime());
        txtPrice.setText(Double.toString(p.getPrice()));
    }

    // update payment
    public void selectPayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentId = txtPaymentName.getText();

        Payment p1 = new PaymentController().getPayment(paymentId);
        if (p1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(p1);
        }
    }

    public void updatePayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment p1 = new Payment(
                txtPaymentName.getText(), txtValidTime.getText(), Double.parseDouble(txtPrice.getText()));

        if (new PaymentController().updatePayment(p1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete payment
    public void deletePayment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
            if (new PaymentController().deletePayment(txtPaymentName.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }

    // calculation of oparater
    public void arithmeticOperation(){
        switch (calculation)
        {
            case 1:
                answer = num + Double.parseDouble(txtAnswer.getText());
                txtAnswer.setText(Double.toString(answer));
                break;

            case 2:
                answer = num - Double.parseDouble(txtAnswer.getText());
                txtAnswer.setText(Double.toString(answer));
                break;

            case 3:
                answer = num * Double.parseDouble(txtAnswer.getText());
                txtAnswer.setText(Double.toString(answer));
                break;

            case 4:
                answer = num / Double.parseDouble(txtAnswer.getText());
                txtAnswer.setText(Double.toString(answer));
                break;
        }
    }

    // calculation of number
    public void input0(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"0");
    }

    public void input1(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"1");
    }

    public void input2(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"2");
    }

    public void input3(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"3");
    }

    public void input4(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"4");
    }

    public void input5(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"5");
    }

    public void input6(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"6");
    }

    public void input7(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"7");
    }

    public void input8(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"8");
    }

    public void input9(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+"9");
    }

    public void btnequal(ActionEvent actionEvent) {
        arithmeticOperation();
        label.setText("");
    }

    public void btnMax(ActionEvent actionEvent) {
        num = Double.parseDouble(txtAnswer.getText());
        calculation =1;
        txtAnswer.setText("");
        label.setText(num + "+");
    }

    public void btnMin(ActionEvent actionEvent) {
        num = Double.parseDouble(txtAnswer.getText());
        calculation =2;
        txtAnswer.setText("");
        label.setText(num + "-");
    }

    public void btnIncrease(ActionEvent actionEvent) {
        num = Double.parseDouble(txtAnswer.getText());
        calculation =3;
        txtAnswer.setText("");
        label.setText(num + "*");
    }

    public void btnDivision(ActionEvent actionEvent) {
        num = Double.parseDouble(txtAnswer.getText());
        calculation =4;
        txtAnswer.setText("");
        label.setText(num + "/");
    }

    public void btnFullStop(ActionEvent actionEvent) {
        txtAnswer.setText(txtAnswer.getText()+".");
    }

    // hide image
    public void hideImageTen(MouseEvent mouseEvent) {
        image.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // open image
    public void openImageTen(MouseEvent mouseEvent) {
        image.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // clear text
    public void clearText(MouseEvent mouseEvent) {
        txtAnswer.clear();
        txtPaymentName.clear();
        txtValidTime.clear();
        txtPrice.clear();
    }

}