package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class OrderFormcontroller {

    public ImageView imageTen;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public JFXComboBox<String> cmbMemberIds;
    public JFXComboBox<String> cmbItemCodes;
    public JFXComboBox<String> cmbSupplementCodes;
    public JFXTextField txtMemberName;
    public JFXTextField txtMemberAddress;
    public JFXTextField txtMemberContact;
    public JFXTextField txtItemQtyOnHand;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtItemName;
    public TableView<CartTM> tblCart;
    public TableColumn colCode;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colName;
    public TextField txtQty;
    public JFXTextField txtTtl;
    public JFXTextField txtDiscount;
    public JFXTextField txtBalance;
    public JFXTextField txtCash;
    public JFXTextField txtRemaining;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public JFXButton btmAddToCart;
    public Label lblTotalOrder;

    double ttl = 0;
    double balance = 0;

    int cartSelectedRowForRemove = -1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyPattern = Pattern.compile("^[1-9]{1,5}$");

    public void initialize() {
        btmAddToCart.setDisable(true);
        storeValidations();
        loadDateAndTime();

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        setOrderId();

        try {
            lblTotalOrder.setText(getCountOrder());
            loadMemberIds();
            loadItemCodes();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbMemberIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMemberData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbItemCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    // set member date
    private void setMemberData(String memberId) throws SQLException, ClassNotFoundException {
        Member m1 = new MemberControler().getMember(memberId);
        if (m1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtMemberName.setText(m1.getName());
            txtMemberAddress.setText(m1.getAddress());
            txtMemberContact.setText(m1.getContact());
        }
    }

    // set item date
    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = new ItemController().getItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtItemName.setText(i1.getName());
            txtItemQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtItemUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
        }
    }

    // load member ids
    private void loadMemberIds() throws SQLException, ClassNotFoundException {
        List<String> memberIds = new MemberControler().getMemberIds();
        cmbMemberIds.getItems().addAll(memberIds);
    }

    // load item codes
    private void loadItemCodes() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new ItemController().getAllItemIds();
        cmbItemCodes.getItems().addAll(itemIds);
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    // add to cart
    public void addToCart(ActionEvent actionEvent) {
        String name = txtItemName.getText();
        int qtyOnHand = Integer.parseInt(txtItemQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        int qtyForMember = Integer.parseInt(txtQty.getText());
        double total = qtyForMember * unitPrice;

        if (qtyOnHand < qtyForMember) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTM tm = new CartTM(
                cmbItemCodes.getValue(),
                name,
                qtyForMember,
                unitPrice,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getCode(),
                    temp.getName(),
                    temp.getQty() + qtyForMember,
                    unitPrice,
                    total + temp.getTotal()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCart.setItems(obList);
        calculateCost();
    }

    // place order
    public void placeOrder(ActionEvent actionEvent) {
        ArrayList<ItemDetail> items = new ArrayList<>();
        double total = 0;
        for (CartTM tempTm : obList
        ) {
            total += tempTm.getTotal();
            items.add(new ItemDetail(
                    tempTm.getCode(),
                    tempTm.getUnitPrice(),
                    tempTm.getQty()
            ));
        }

        Order order = new Order(
                lblOrderId.getText(),
                cmbMemberIds.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                total,
                items
        );

        if (new OrderController().placeOrder(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setOrderId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // set order id
    private void setOrderId() {
        try {
            lblOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // clear row
    public void clearRow(MouseEvent mouseEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select A Row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    // is exists
    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    // gross amount
    void calculateCost() {
        for (CartTM tm : obList) {
            ttl += tm.getTotal();
        }
        txtTtl.setText(ttl + "/=");
    }

    // discount
    public void selectDiscount(ActionEvent actionEvent) {
        double discount = Float.parseFloat(txtDiscount.getText());
        double cal = ttl * discount / 100;
        double lastbalance = ttl - cal;
        txtBalance.setText(String.format("%.1f", lastbalance));
        balance = lastbalance;
    }

    // Remaining
    public void selectCash(ActionEvent actionEvent) {
        double cash = Float.parseFloat(txtCash.getText());
        double calc = cash - balance;
        txtRemaining.setText(String.format("%.1f", calc));
    }

    // store validation
    private void storeValidations() {
        map.put(txtQty, qtyPattern);
    }

    // textFields key released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate3(map, btmAddToCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Details are correct!").showAndWait();
            }
        }
    }

    // hide images
    public void hideImageTen(MouseEvent mouseEvent) {
        imageTen.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display images
    public void openImageTen(MouseEvent mouseEvent) {
        imageTen.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // date & time
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    // clear text
    public void clearText(MouseEvent mouseEvent) {
        txtRemaining.clear();
        txtCash.clear();
        txtBalance.clear();
        txtDiscount.clear();
        txtTtl.clear();
        txtMemberName.clear();
        txtMemberAddress.clear();
        txtMemberContact.clear();
        txtItemName.clear();
        txtItemUnitPrice.clear();
        txtItemQtyOnHand.clear();
        cmbItemCodes.getSelectionModel().clearSelection();
        cmbMemberIds.getSelectionModel().clearSelection();
    }

    // order report
    public void orderReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Order_Report.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            String memberId = cmbMemberIds.getValue();
            String itemCode = cmbItemCodes.getValue();
            String itemName = txtItemName.getText();
            String qty = txtQty.getText();
            String discount = txtDiscount.getText();
            String balance = txtBalance.getText();
            String orderId = lblOrderId.getText();
            String memberName = txtMemberName.getText();

            HashMap map = new HashMap();
            map.put("memberId", memberId);
            map.put("itemCode", itemCode);
            map.put("itemName", itemName);
            map.put("qty", qty);
            map.put("discount", discount);
            map.put("balance", balance);
            map.put("orderId", orderId);
            map.put("coachName", memberName);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // get count order
    public String getCountOrder() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT sum(price) FROM `orderDetail` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // open order detail report
    public void openOrderDetailReport (MouseEvent mouseEvent){
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/OrderDetailReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

                String totalOrderPrice = lblTotalOrder.getText();

                HashMap map = new HashMap();
                map.put("total", totalOrderPrice);

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