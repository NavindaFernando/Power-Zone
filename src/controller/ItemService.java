package controller;

import model.Item;
import model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemService {
    public boolean saveItem(Item i) throws SQLException, ClassNotFoundException;
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    public Item getItem(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Item> getAllItem() throws SQLException, ClassNotFoundException;
}
