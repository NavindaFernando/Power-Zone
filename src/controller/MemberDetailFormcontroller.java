package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.MemberTM;

import java.io.IOException;
import java.net.URL;

public class MemberDetailFormcontroller {

    public AnchorPane memberDetailsContext;


    public void setData(TableView<MemberTM> tm){
        System.out.println(tm);
    }

    // open members form
    public void openMembersFrom(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/MembersForm.fxml");
        Parent load = FXMLLoader.load(resource);
        memberDetailsContext.getChildren().clear();
        memberDetailsContext.getChildren().add(load);
    }
}
