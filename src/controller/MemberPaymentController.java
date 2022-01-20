package controller;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberPaymentController {

    public String getPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT paymentId FROM memberPayment ORDER BY paymentId DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "O-00"+tempId;
            }else if (tempId<99){
                return "O-0"+tempId;
            }else {
                return "O-"+tempId;
            }

        }else {
            return "O-001";
        }
    }

    public boolean placePayment(MemberPayment payment){

        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `memberPayment` VALUES(?,?,?,?,?)");
            stm.setObject(1,payment.getPaymentId());
            stm.setObject(2,payment.getMemberId());
            stm.setObject(3,payment.getPaymentDate());
            stm.setObject(4,payment.getPaymentTime());
            stm.setObject(5,payment.getCost());

            if (stm.executeUpdate()>0){
                return savePaymentDetails(payment.getPaymentId(),payment.getPayments());
            }else {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean savePaymentDetails(String paymentId, ArrayList<PaymentDetail> payments) throws SQLException, ClassNotFoundException {
        for (PaymentDetail temp : payments
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `paymentDetail` VALUES(?,?,?)");
            stm.setObject(1,paymentId);
            stm.setObject(2,temp.getMemberId());
            stm.setObject(3,temp.getPrice());

            if (stm.executeUpdate()>0){
                }else {
                    return false;
                }
        }
        return true;
    }
}
