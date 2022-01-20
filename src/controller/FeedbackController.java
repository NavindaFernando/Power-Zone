package controller;

import db.DbConnection;
import model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackController implements FeedbackService{

    public List<String> getFeedbackIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Feedback").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Feedback
    public boolean saveFeedback(Feedback f) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Feedback VALUES(?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, f.getDate());
        stm.setObject(2, f.getMassage());
        return stm.executeUpdate() > 0;
    }

    @Override // update Coach
    public boolean updateFeedback(Feedback f) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Feedback SET massage=? WHERE feedbackDate=?");
        stm.setObject(1, f.getMassage());
        stm.setObject(2, f.getDate());
        return stm.executeUpdate() > 0;
    }

    @Override // delete Feedback
    public boolean deleteFeedback(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Feedback WHERE feedbackDate='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search Feedback
    public Feedback getFeedback(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Feedback WHERE feedbackDate=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Feedback(
                    rst.getString(1),
                    rst.getString(2)
            );

        } else {
            return null;
        }
    }

    @Override // get All Feedback
    public ArrayList<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Feedback");
        ResultSet rst = stm.executeQuery();
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        while (rst.next()) {
            feedbacks.add(new Feedback(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return feedbacks;
    }
}
