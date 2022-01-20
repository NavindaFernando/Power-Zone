package controller;

import model.TimeTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeTableService {
    public boolean saveTime(TimeTable t) throws SQLException, ClassNotFoundException;
    public boolean updateTime(TimeTable t) throws SQLException, ClassNotFoundException;
    public boolean deleteTime(String id) throws SQLException, ClassNotFoundException;
    public TimeTable getTime(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<TimeTable> getAllTime() throws SQLException, ClassNotFoundException;
}
