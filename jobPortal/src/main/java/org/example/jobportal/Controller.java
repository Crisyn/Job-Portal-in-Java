package org.example.jobportal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private Label name;
    @FXML
    private TextField searchField;

    //Get Job Infos from DB where searchbar is like jobName
    DatabaseConnection db = new DatabaseConnection();
    Connection conn = db.getConnection();
    private String searchBar = "";

    @FXML
    public void initialize() {
        searchField.textProperty().addListener((o, oldP, newP) -> {
            searchBar = newP;
            displayJobName();
        });

        displayJobName();
    }

    public void displayJobName() {


        String query = "select jobName from jobInfo where jobName like " + "'" + searchBar + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                name.setText(rs.getString("jobName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jobShortDescription(ActionEvent event) {
        String query = "select jobShortDescription from jobInfo where jobName like ";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jobLocation(ActionEvent event) {
        String query = "select jobLocation from jobInfo where jobName like ";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emplymentType(ActionEvent event) {
        String query = "select emplymentType from jobInfo where jobName like ";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}