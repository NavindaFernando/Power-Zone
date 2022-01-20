package controller;

import db.DbConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService{

    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException, SQLException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Item
    public boolean saveItem(Item i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, i.getItemCode());
        stm.setObject(2, i.getName());
        stm.setObject(3, i.getDescription());
        stm.setObject(4, i.getQtyOnHand());
        stm.setObject(5, i.getSize());
        stm.setObject(6, i.getUnitPrice());
        return stm.executeUpdate() > 0;
    }

    @Override // update Item
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET name=?, Description=?, qtyOnHand=?, Sizes=?, unitPrice=? WHERE itemCode=?");
        stm.setObject(1, i.getName());
        stm.setObject(2, i.getDescription());
        stm.setObject(3, i.getQtyOnHand());
        stm.setObject(4, i.getSize());
        stm.setObject(5, i.getUnitPrice());
        stm.setObject(6, i.getItemCode());

        return stm.executeUpdate() > 0;
    }

    @Override // delete Item
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE itemCode='"+code+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search Item
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE itemCode=?");
        stm.setObject(1, code);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getDouble(6)
                    );

        } else {
            return null;
        }
    }

    @Override // get All Item
    public ArrayList<Item> getAllItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return items;
    }
}
