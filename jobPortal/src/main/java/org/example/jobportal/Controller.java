package org.example.jobportal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private Label showJobName;

    private String searchbarOut = "IT";
    //Get Job Infos from DB where searchbar is like jobName
    DatabaseConnection db = new DatabaseConnection();
    Connection conn = db.getConnection();

    public void displayJobName() {


        String query = "select jobName from jobInfo where jobName like " + "'%" + searchbarOut + "%'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                showJobName.setText(rs.getString("jobName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void jobShortDescription(ActionEvent event) {
        String query = "select jobShortDescription from jobInfo where jobName like " + "'%" + searchbarOut + "%'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jobLocation(ActionEvent event) {
        String query = "select jobLocation from jobInfo where jobName like " + "'%" + searchbarOut + "%'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emplymentType(ActionEvent event) {
        String query = "select emplymentType from jobInfo where jobName like " + "'%" + searchbarOut + "%'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}