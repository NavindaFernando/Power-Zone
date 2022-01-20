package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Feedback;
import model.TimeTable;
import view.tm.FeedbackTM;
import view.tm.TimeTableTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class CoachTimeTableFormcontroller {
    public TableView tblTimeTable;
    public TableColumn colDay;
    public TableColumn colBranchName;
    public TableColumn colAmToPm;
    public TableColumn colPmToPm;
    public TableColumn colPmToAm;
    public AnchorPane timeTableContext;
    public TableView tblFeedBack;
    public TableColumn colDate;
    public TableColumn colMassages;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public ImageView image;

    public void initialize(){
         try {

            colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
            colBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
            colAmToPm.setCellValueFactory(new PropertyValueFactory<>("amToPm"));
            colPmToPm.setCellValueFactory(new PropertyValueFactory<>("pmToPm"));
            colPmToAm.setCellValueFactory(new PropertyValueFactory<>("pmToAm"));

            setTimeToTable(new TimeController().getAllTime());

            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colMassages.setCellValueFactory(new PropertyValueFactory<>("massage"));

            setMassageToTable(new FeedbackController().getAllFeedback());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // set time to table
    private void setTimeToTable(ArrayList<TimeTable> times) {
        ObservableList<TimeTableTM> obList = FXCollections.observableArrayList();
        times.forEach(e -> {
            obList.add(
                    new TimeTableTM(e.getDay(), e.getBranchName(), e.getAmToPm(), e.getPmToPm(), e.getPmToAm()));
        });
        tblTimeTable.setItems(obList);
    }

    // set massage to table
    private void setMassageToTable(ArrayList<Feedback> feedbacks) {
        ObservableList<FeedbackTM> obList = FXCollections.observableArrayList();
        feedbacks.forEach(e -> {
            obList.add(
                    new FeedbackTM(e.getDate(), e.getMassage()));
        });
        tblFeedBack.setItems(obList);
    }

    // image hide
    public void hideImage(MouseEvent mouseEvent) {
        image.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // image open
    public void openImage(MouseEvent mouseEvent) {
        image.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }
}
