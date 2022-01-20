package controller;

import model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberService {
    public boolean saveMember(Member m) throws SQLException, ClassNotFoundException;
    public boolean updateMember(Member m) throws SQLException, ClassNotFoundException;
    public boolean deleteMember(String id) throws SQLException, ClassNotFoundException;
    public Member getMember(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Member> getAllMember() throws SQLException, ClassNotFoundException;
}
