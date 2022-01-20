package controller;

import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminHomeFormcontroller {

    public ImageView imageFive;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;
    public Label lblCoach;
    public Label lblMember;
    public Label lblItem;
    public Label lblOrder;
    public Label lblDailyIncome;
    public Label lblPeding;
    public LineChart lineChart;

    public void initialize(){

        loadChart();

        try {

            lblCoach.setText(getCountCoach());
            lblMember.setText(getCountMember());
            lblItem.setText(getCountItem());
            lblOrder.setText(getCountOrder());
            lblDailyIncome.setText(getCountOrderTotal());
            lblPeding.setText(getCountqty());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // load chart
    private void loadChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("1",20));
        series.getData().add(new XYChart.Data("2",18));
        series.getData().add(new XYChart.Data("3",35));
        series.getData().add(new XYChart.Data("4",15));
        series.getData().add(new XYChart.Data("5",24));
        series.getData().add(new XYChart.Data("6",10));
        series.getData().add(new XYChart.Data("7",30));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data("1",50));
        series2.getData().add(new XYChart.Data("2",38));
        series2.getData().add(new XYChart.Data("3",15));
        series2.getData().add(new XYChart.Data("4",45));
        series2.getData().add(new XYChart.Data("5",34));
        series2.getData().add(new XYChart.Data("6",20));
        series2.getData().add(new XYChart.Data("7",60));

        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data("1",80));
        series3.getData().add(new XYChart.Data("2",78));
        series3.getData().add(new XYChart.Data("3",65));
        series3.getData().add(new XYChart.Data("4",58));
        series3.getData().add(new XYChart.Data("5",64));
        series3.getData().add(new XYChart.Data("6",70));
        series3.getData().add(new XYChart.Data("7",70));

        XYChart.Series series4 = new XYChart.Series();
        series4.getData().add(new XYChart.Data("1",70));
        series4.getData().add(new XYChart.Data("2",62));
        series4.getData().add(new XYChart.Data("3",55));
        series4.getData().add(new XYChart.Data("4",31));
        series4.getData().add(new XYChart.Data("5",44));
        series4.getData().add(new XYChart.Data("6",50));
        series4.getData().add(new XYChart.Data("7",45));

        lineChart.getData().addAll(series,series2,series3,series4);
  //    series.getNode().setStyle("-fx-stroke: #26ff00");
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

    // member count
    public String getCountMember() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(id) FROM member ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // item count
    public String getCountItem() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(itemCode) FROM item ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // order count
    public String getCountOrder() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(orderId) FROM `order` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // get count order total
    public String getCountOrderTotal() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT sum(price) FROM `orderDetail` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // get count qty
    public String getCountqty() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT sum(qty) FROM `orderDetail` ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    // hide image
    public void hideImageFive(MouseEvent mouseEvent) {
        imageFive.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    // open image
    public void openImageFive(MouseEvent mouseEvent) {
        imageFive.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }
}
