package controller;

import model.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentService {
    public boolean savePayment(Payment p) throws SQLException, ClassNotFoundException;
    public boolean updatePayment(Payment p) throws SQLException, ClassNotFoundException;
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    public Payment getPayment(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Payment> getAllPayment() throws SQLException, ClassNotFoundException;
}
