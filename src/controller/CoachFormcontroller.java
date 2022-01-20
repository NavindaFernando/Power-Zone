package controller;

import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Coach;
import model.Member;
import view.tm.CoachTM;
import view.tm.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoachFormcontroller {

    public ImageView imageSix;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public AnchorPane coachContext;
    public TableView<CoachTM> tblCoachDetails;
    public TableColumn colCoachId;
    public TableColumn colCoachName;
    public TableColumn colCoachContact;
    public TableColumn colCoachGender;
    public TableColumn colCoachAddress;
    public TableColumn colCoachAge;
    public TableColumn colCoachUsername;
    public TableColumn colCoachPassword;
    public Label lblCoach;

    public void initialize() {

        try {

            lblCoach.setText(getCountCoach());

            colCoachId.setCellValueFactory(new PropertyValueFactory<>("coachId"));
            colCoachName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCoachContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colCoachGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colCoachAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCoachAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            colCoachUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colCoachPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

            setCoachToTable(new CoachController().getAllCoach());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // set coach to table
    private void setCoachToTable(ArrayList<Coach> coachs) {
        ObservableList<CoachTM> obList = FXCollections.observableArrayList();
        coachs.forEach(e -> {
            obList.add(
                    new CoachTM(e.getCoachId(), e.getName(), e.getContact(), e.getGender(), e.getAddress(),e.getAge(),e.getUserName(),e.getPassword()));
        });
        tblCoachDetails.setItems(obList);
    }

    // coach count
    public String getCountCoach() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(coachId) FROM coach ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // hide image
    public void hideImageSix(MouseEvent mouseEvent) {
        imageSix.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // open image
    public void openImageSix(MouseEvent mouseEvent) {
        imageSix.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // open time table
    public void openTimeTable(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/TimeTable.fxml");
        Parent load = FXMLLoader.load(resource);
        coachContext.getChildren().clear();
        coachContext.getChildren().add(load);
    }
}
