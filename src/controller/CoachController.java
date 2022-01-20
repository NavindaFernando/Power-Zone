package controller;

import db.DbConnection;
import model.Coach;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachController implements CoachService{

    public List<String> getCoachIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Coach").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Coach
    public boolean saveCoach(Coach c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Coach VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getCoachId());
        stm.setObject(2, c.getName());
        stm.setObject(3, c.getContact());
        stm.setObject(4, c.getGender());
        stm.setObject(5, c.getAddress());
        stm.setObject(6, c.getAge());
        stm.setObject(7, c.getUserName());
        stm.setObject(8, c.getPassword());
        return stm.executeUpdate() > 0;
    }

    @Override // update Coach
    public boolean updateCoach(Coach c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Coach SET name=?, contact=?, gender=?, address=?, age=?, userName=?, password=? WHERE coachId=?");
        stm.setObject(1, c.getName());
        stm.setObject(2, c.getContact());
        stm.setObject(3, c.getGender());
        stm.setObject(4, c.getAddress());
        stm.setObject(5, c.getAge());
        stm.setObject(6, c.getUserName());
        stm.setObject(7, c.getPassword());
        stm.setObject(8, c.getCoachId());

        return stm.executeUpdate() > 0;
    }

    @Override // delete Coach
    public boolean deleteCoach(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Coach WHERE coachId='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search Coach
    public Coach getCoach(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Coach WHERE coachId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Coach(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)
            );

        } else {
            return null;
        }
    }

    @Override // get All Coach
    public ArrayList<Coach> getAllCoach() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Coach");
        ResultSet rst = stm.executeQuery();
        ArrayList<Coach> coachs = new ArrayList<>();
        while (rst.next()) {
            coachs.add(new Coach(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return coachs;
    }
}
