package controller;

import com.jfoenix.controls.JFXTreeTableView;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;
import view.tm.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MembersFormcontroller extends DashBoardFormcontroller{

    public ImageView imageNine;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public TableView<MemberTM> tblMemberDetails;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colGender;
    public TableColumn colAddress;
    public TableColumn colDateOfBirth;
    public TableColumn colOccupation;
    public Label lblMember;
    public TextField txtSearchMember;
    public AnchorPane memberContext;

    // load table Customer
    public void initialize() {

        try {

            lblMember.setText(getCountMember());

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
            colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));

            setMemberToTable(new MemberControler().getAllMember());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /*txtSearchMember.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });*/

        tblMemberDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadMemberDetailsUi(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    // load member detail form
    private void loadMemberDetailsUi(MemberTM newValue) throws IOException {

/*        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MemberDetailForm.fxml"));
        Parent load = fxmlLoader.load();
        MemberDetailFormcontroller controller = fxmlLoader.getController();
        controller.setData(tblMemberDetails);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();*/

        URL resource = getClass().getResource("../view/MemberDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
/*      MemberDetailFormcontroller controller = new MemberDetailFormcontroller();
        controller.setData(tblMemberDetails);*/
        memberContext.getChildren().clear();
        memberContext.getChildren().add(load);
    }

    // set member to table
    private void setMemberToTable(ArrayList<Member> members) {
        ObservableList<MemberTM> obList = FXCollections.observableArrayList();
        members.forEach(e -> {
            obList.add(
                    new MemberTM(e.getId(), e.getName(), e.getContact(), e.getGender(), e.getAddress(),e.getBirthday(),e.getOccupation()));
        });
        tblMemberDetails.setItems(obList);
    }

    // member count
    public String getCountMember() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(id) FROM member ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // clear member into table
    public void clearMemeber(MouseEvent mouseEvent) {

    }

    // image hide
    public void hideImageNine(MouseEvent mouseEvent) {
        imageNine.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // image display
    public void openImageNine(MouseEvent mouseEvent) {
        imageNine.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }

    // member detail report
    public void memberDetailReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/MemberDetailReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            String memberCount = lblMember.getText();
            HashMap map = new HashMap();
            map.put("memberCount", memberCount);
            ObservableList<MemberTM> items = tblMemberDetails.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // search member
    public void search(String value){
        /*try {
            List<Member> members = MemberControler.getMember(value);
            ObservableList<MemberTM> tableData = FXCollections.observableArrayList();
            for (Member member: members) {

                tableData.add(new MemberTM(
                        member.getId(),
                        member.getName(),
                        member.getContact(),
                        member.getGender(),
                        member.getAddress(),
                        member.getBirthday(),
                        member.getOccupation()
                ));
            }
            tblMemberDetails.getItems().setAll(tableData);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
