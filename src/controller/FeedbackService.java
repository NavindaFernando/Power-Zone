package controller;

import model.Coach;
import model.Feedback;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FeedbackService {
    public boolean saveFeedback(Feedback f) throws SQLException, ClassNotFoundException;
    public boolean updateFeedback(Feedback f) throws SQLException, ClassNotFoundException;
    public boolean deleteFeedback(String id) throws SQLException, ClassNotFoundException;
    public Feedback getFeedback(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException;
}
