package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import util.ValidationUtil;
import view.tm.CartTM;
import view.tm.PaymentCartTM;
import view.tm.PaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentFormcontroller {

    public AnchorPane paymentContext;
    public ImageView imageTwelve;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXComboBox cmbMemberIds;
    public JFXComboBox cmbPaymentName;
    public JFXTextField txtValidTime;
    public JFXTextField txtPrice;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public TableView tblCartPayment;
    public TableColumn colPaymentMethod;
    public TableColumn colPrice;
    public TableColumn colValidTime;
    public JFXTextField txtTtl;
    public TextField txtQty;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXTextField txtCash;
    public JFXTextField txtRemaining;
    public Label lblPaymentId;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnAddToCart;

    int cartSelectedRowForRemove = -1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyPattern = Pattern.compile("^[1-9]{1,5}$");

    public void initialize(){
            btnAddToCart.setDisable(true);
            storeValidations();
            setPaymentId();

        try {
            loadDateAndTime();
            loadMemberIds();
            loadPaymentNames();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colValidTime.setCellValueFactory(new PropertyValueFactory<>("validTime"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbMemberIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMemberData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbPaymentName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setPaymentData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblCartPayment.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    // set member date
    private void setMemberData(String memberId) throws SQLException, ClassNotFoundException {
        Member m1 = new MemberControler().getMember(memberId);
        if (m1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtName.setText(m1.getName());
            txtContact.setText(m1.getContact());
        }
    }

    // set payement date
    private void setPaymentData(String paymentCode) throws SQLException, ClassNotFoundException {
        Payment p1 = new PaymentController().getPayment(paymentCode);
        if (p1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtValidTime.setText(p1.getValidTime());
            txtPrice.setText(String.valueOf(p1.getPrice()));
        }
    }

    // load member ids
    private void loadMemberIds() throws SQLException, ClassNotFoundException {
        List<String> memberIds = new MemberControler().getMemberIds();
        cmbMemberIds.getItems().addAll(memberIds);
    }

    // load payment name
    private void loadPaymentNames() throws SQLException, ClassNotFoundException {
        List<String> paymentIds = new PaymentController().getPaymentIds();
        cmbPaymentName.getItems().addAll(paymentIds);
    }

    ObservableList<PaymentCartTM> obList = FXCollections.observableArrayList();

    // add to payment
    public void addToPayment(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtPrice.getText());
        String time = txtValidTime.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty*unitPrice;

        PaymentCartTM tm = new PaymentCartTM(
                (String) cmbPaymentName.getValue(),
                unitPrice,
                time,
                qty,
                total
        );
        int rowNumber = isExists(tm);

        if (rowNumber==-1) {
            obList.add(tm);
        }else {
            PaymentCartTM temp = obList .get(rowNumber);
            PaymentCartTM newTm = new PaymentCartTM(
                    temp.getPaymentName(),
                    temp.getPrice(),
                    temp.getValidTime(),
                    qty,
                    total+temp.getTotal()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCartPayment.setItems(obList);
        calculateCost();
    }

    // is exists
    private int isExists(PaymentCartTM tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getPaymentName().equals(obList.get(i).getPaymentName())){
                return i;
            }
        }
        return -1;
    }

    // gross amount
    void calculateCost(){
        double ttl = 0;
        for (PaymentCartTM tm : obList){
            ttl+=tm.getTotal();
        }
        txtTtl.setText(ttl+"/=");
    }

    // place payment
    public void placePayment(ActionEvent actionEvent) {
        ArrayList<PaymentDetail> payments = new ArrayList<>();
        double ttl = 0;
        for (PaymentCartTM tempTm: obList
        ) {
            ttl+=tempTm.getTotal();
            payments.add(new PaymentDetail(
                    tempTm.getPaymentName(),
                    (String) cmbMemberIds.getValue(),
                    tempTm.getTotal()
            ));
        }

        MemberPayment memberPayment = new MemberPayment(
                lblPaymentId.getText(),
                (String) cmbMemberIds.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                ttl,
                payments
        );

        if (new MemberPaymentController().placePayment(memberPayment)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
            setPaymentId();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    // set payment id
    private void setPaymentId() {
        try {
            lblPaymentId.setText(new MemberPaymentController().getPaymentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // store validation
    private void storeValidations() {
        map.put(txtQty, qtyPattern);
    }

    // textFields key released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate3(map, btnAddToCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Details are correct!").showAndWait();
            }
        }
    }

    // date & time
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
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

    // clear table row
    public void clearRow(MouseEvent mouseEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please Select A Row").show();
        }else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCartPayment.refresh();
        }
    }

    // hide image
    public void hideImageTwelve(MouseEvent mouseEvent) {
        imageTwelve.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // open image
    public void openImageTwelve(MouseEvent mouseEvent) {
        imageTwelve.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }
}