package controller;

import model.Coach;
import model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CoachService {
    public boolean saveCoach(Coach c) throws SQLException, ClassNotFoundException;
    public boolean updateCoach(Coach c) throws SQLException, ClassNotFoundException;
    public boolean deleteCoach(String id) throws SQLException, ClassNotFoundException;
    public Coach getCoach(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Coach> getAllCoach() throws SQLException, ClassNotFoundException;
}
