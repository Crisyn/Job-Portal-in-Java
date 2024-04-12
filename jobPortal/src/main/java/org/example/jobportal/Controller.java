package org.example.jobportal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Controller {

    @FXML
    private Label name;
    @FXML
    private TextField searchField;

    private String searchbarOut = "IT";
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
    private Scene scene;
    private Stage stage;
    private Parent root;

    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-job.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    public void addJob(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();

    }
}