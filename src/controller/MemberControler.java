package controller;

import db.DbConnection;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberControler implements MemberService{

    public List<String> getMemberIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Member").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override // save Member
    public boolean saveMember(Member m) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Member VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, m.getId());
        stm.setObject(2, m.getName());
        stm.setObject(3, m.getContact());
        stm.setObject(4, m.getGender());
        stm.setObject(5, m.getAddress());
        stm.setObject(6, m.getBirthday());
        stm.setObject(7, m.getOccupation());
        return stm.executeUpdate() > 0;
    }

    @Override // update Member
    public boolean updateMember(Member m) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Member SET name=?, contact=?, gender=?, address=?, birthday=?, occupation=? WHERE id=?");
        stm.setObject(1, m.getName());
        stm.setObject(2, m.getContact());
        stm.setObject(3, m.getGender());
        stm.setObject(4, m.getAddress());
        stm.setObject(5, m.getBirthday());
        stm.setObject(6, m.getOccupation());
        stm.setObject(7, m.getId());

        return stm.executeUpdate() > 0;
    }

    @Override // delete Member
    public boolean deleteMember(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Member WHERE id='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override // search Member
    public Member getMember(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Member WHERE id=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Member(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }

    @Override // get All Member
    public ArrayList<Member> getAllMember() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Member");
        ResultSet rst = stm.executeQuery();
        ArrayList<Member> members = new ArrayList<>();
        while (rst.next()) {
            members.add(new Member(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)

            ));
        }
        return members;
    }
}
