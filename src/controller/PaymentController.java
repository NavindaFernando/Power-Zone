package controller;

import db.DbConnection;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController implements PaymentService{

    public List<String> getPaymentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Payment
    public boolean savePayment(Payment p) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Payment VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, p.getPaymentName());
        stm.setObject(2, p.getValidTime());
        stm.setObject(3, p.getPrice());
        return stm.executeUpdate() > 0;
    }

    @Override // update Payment
    public boolean updatePayment(Payment p) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Payment SET validTime=?, price=? WHERE paymentName=?");
        stm.setObject(1, p.getValidTime());
        stm.setObject(2, p.getPrice());
        stm.setObject(3, p.getPaymentName());

        return stm.executeUpdate() > 0;
    }

    @Override // delete Payment
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Payment WHERE paymentName='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search Payment
    public Payment getPayment(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE paymentName=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            );

        } else {
            return null;
        }
    }

    @Override // get All Payment
    public ArrayList<Payment> getAllPayment() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment");
        ResultSet rst = stm.executeQuery();
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
        }
        return payments;
    }
}
