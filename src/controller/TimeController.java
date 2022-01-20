package controller;

import db.DbConnection;
import model.TimeTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeController implements TimeTableService {

    public List<String> getDayIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM TimeTable").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Time
    public boolean saveTime(TimeTable t) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO TimeTable VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, t.getDay());
        stm.setObject(2, t.getBranchName());
        stm.setObject(3, t.getAmToPm());
        stm.setObject(4, t.getPmToPm());
        stm.setObject(5, t.getPmToAm());
        return stm.executeUpdate() > 0;
    }

    @Override // update Time
    public boolean updateTime(TimeTable t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE TimeTable SET branchName=?, amToPm=?, pmToPm=?, pmToAm=? WHERE days=?");
        stm.setObject(1, t.getBranchName());
        stm.setObject(2, t.getAmToPm());
        stm.setObject(3, t.getPmToPm());
        stm.setObject(4, t.getPmToAm());
        stm.setObject(5, t.getDay());
        return stm.executeUpdate() > 0;
    }

    @Override // delete time
    public boolean deleteTime(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM TimeTable WHERE days='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search time
    public TimeTable getTime(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM TimeTable WHERE days=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new TimeTable(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }

    @Override // get All Time
    public ArrayList<TimeTable> getAllTime() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM TimeTable");
        ResultSet rst = stm.executeQuery();
        ArrayList<TimeTable> times = new ArrayList<>();
        while (rst.next()) {
            times.add(new TimeTable(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return times;
    }
}
