package controller;

import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Item;
import model.Member;
import view.tm.ItemTM;
import view.tm.MemberTM;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormcontroller {
    public ImageView imageEight;
    public ImageView imageEightt;
    public ImageView imageEighttt;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public TableView<ItemTM> tblItemDetail;
    public TableColumn colItemCode;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colSize;
    public TextField txtSearch;
    public Label lblItems;
    public Label lblOrders;


    public void initialize() {

        try {

            lblItems.setText(getCountItem());
            lblOrders.setText(getCountOrder());

            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

            setItemToTable(new ItemController().getAllItem());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // set item to table
    public void setItemToTable(ArrayList<Item> items) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        items.forEach(e -> {
            obList.add(
                    new ItemTM(e.getItemCode(), e.getName(), e.getDescription(), e.getQtyOnHand(), e.getSize(),e.getUnitPrice()));
        });
        tblItemDetail.setItems(obList);
    }

    // item count
    public String getCountItem() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(itemCode) FROM Item ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // order count
    public String getCountOrder() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(orderId) FROM `order` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // hide image
    public void hideImageEight(MouseEvent mouseEvent) {
        imageEight.setVisible(false);
        imageEightt.setVisible(false);
        imageEighttt.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display image
    public void openImageEight(MouseEvent mouseEvent) {
        imageEight.setVisible(true);
        imageEightt.setVisible(true);
        imageEighttt.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

}
