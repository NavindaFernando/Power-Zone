package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Coach;
import model.Item;
import model.Member;
import model.TimeTable;
import util.ValidationUtil;
import view.tm.CoachTM;
import view.tm.ItemTM;
import view.tm.TimeTableTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class TimeTablecontroller {

    public ImageView imageFour;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public TableView tblTimeTable;
    public TableColumn colDay;
    public TableColumn colBranchName;
    public TableColumn colAmToPm;
    public TableColumn colPmToPm;
    public TableColumn colPmToAm;
    public JFXTextField txtAmToPm;
    public JFXTextField txtPmToPm;
    public JFXTextField txtPmToAm;
    public JFXTextField txtBranchName;
    public JFXTextField txtDays;
    public JFXComboBox cmbCoachIds;
    public JFXComboBox cmbCoachIdsPmToPm;
    public JFXComboBox cmbCoachIdsPmToAm;
    public JFXButton btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern dayPattern = Pattern.compile("^[A-z ]{6,10}$");
    Pattern branchPattern = Pattern.compile("^[A-z ]{3,50}$");

    public void initialize(){
        btnAdd.setDisable(true);
        storeValidations();

        try {

            loadCoachIdsAmToPm();
            loadCoachIdsPmToPm();
            loadCoachIdsPmToAm();

            colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
            colBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
            colAmToPm.setCellValueFactory(new PropertyValueFactory<>("amToPm"));
            colPmToPm.setCellValueFactory(new PropertyValueFactory<>("pmToPm"));
            colPmToAm.setCellValueFactory(new PropertyValueFactory<>("pmToAm"));

            setTimeToTable(new TimeController().getAllTime());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCoachIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCoachData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbCoachIdsPmToPm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCoachDataPmToPm((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbCoachIdsPmToAm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCoachDataPmToAm((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
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

    // set data to am to pm
    private void setCoachData(String coachIds) throws SQLException, ClassNotFoundException {
        Coach c1 = new CoachController().getCoach(coachIds);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtAmToPm.setText(c1.getCoachId());
        }
    }

    // set data to pm to pm
    private void setCoachDataPmToPm(String coachIds) throws SQLException, ClassNotFoundException {
        Coach c1 = new CoachController().getCoach(coachIds);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtPmToPm.setText(c1.getCoachId());
        }
    }

    // set data to pm to am
    private void setCoachDataPmToAm(String coachIds) throws SQLException, ClassNotFoundException {
        Coach c1 = new CoachController().getCoach(coachIds);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else {
            txtPmToAm.setText(c1.getCoachId());
        }
    }

    // load coach id am to pm
    private void loadCoachIdsAmToPm() throws SQLException, ClassNotFoundException {
         List<String> coachIds = new CoachController().getCoachIds();
         cmbCoachIds.getItems().addAll(coachIds);
    }

    // load coach id pm to pm
    private void loadCoachIdsPmToPm() throws SQLException, ClassNotFoundException {
        List<String> coachIds = new CoachController().getCoachIds();
        cmbCoachIdsPmToPm.getItems().addAll(coachIds);
    }

    // load coach id pm to am
    private void loadCoachIdsPmToAm() throws SQLException, ClassNotFoundException {
        List<String> coachIds = new CoachController().getCoachIds();
        cmbCoachIdsPmToAm.getItems().addAll(coachIds);
    }

    // save time
    public void saveTime(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TimeTable t1 = new TimeTable(
                txtDays.getText(), txtBranchName.getText(), txtAmToPm.getText(), txtPmToPm.getText(), txtPmToAm.getText());

        if (new TimeController().saveTime(t1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    // search time
    public void searchTime(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String day = txtDays.getText();

        TimeTable t1 = new TimeController().getTime(day);
        if (t1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(t1);
        }
    }

    void setData(TimeTable t) {
        txtDays.setText(t.getDay());
        txtBranchName.setText(t.getBranchName());
        txtAmToPm.setText(t.getAmToPm());
        txtPmToPm.setText(t.getPmToPm());
        txtPmToAm.setText(t.getPmToAm());
    }

    // update time
    public void updateTime(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TimeTable t1 = new TimeTable(
                txtDays.getText(), txtBranchName.getText(), txtAmToPm.getText(), txtPmToPm.getText(), txtPmToAm.getText());

        if (new TimeController().updateTime(t1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    // delete Time
    public void deleteTime(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (new TimeController().deleteTime(txtDays.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    // store validation
    private void storeValidations() {
        map.put(txtDays, dayPattern);
        map.put(txtBranchName, branchPattern);
    }

    // textFields key released
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate2(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Details are correct!").showAndWait();
            }
        }
    }

    // hide image
    public void hideImageFour(MouseEvent mouseEvent) {
        imageFour.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // display image
    public void openImageFour(MouseEvent mouseEvent) {
        imageFour.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // clear text
    public void clearTextField(MouseEvent mouseEvent) {
        txtDays.clear();
        txtBranchName.clear();
        txtAmToPm.clear();
        txtPmToPm.clear();
        txtPmToAm.clear();
        cmbCoachIds.getSelectionModel().clearSelection();
        cmbCoachIdsPmToPm.getSelectionModel().clearSelection();
        cmbCoachIdsPmToAm.getSelectionModel().clearSelection();
    }
}
