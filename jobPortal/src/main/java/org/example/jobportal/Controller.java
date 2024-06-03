package org.example.jobportal;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class Controller {

    private Gson gson = new Gson();
    @FXML
    public VBox scrollPaneVBox = new VBox();
    @FXML
    public ScrollPane scrollPane = new ScrollPane();
    @FXML
    public TextField jobName;
    @FXML
    public TextField jobLocation;
    @FXML
    public TextField jobDescription;
    @FXML
    public TextField jobEmploymentType;
    @FXML
    public TextField searchField;
    @FXML
    public TextField jobEMail;

    HttpClient httpClient = HttpClient.newBuilder().build();

    @FXML
    public void initialize() {
        // displayJobName();
        try {
            fetchJobs();
            scrollPaneVBox.prefWidthProperty().bind(scrollPane.widthProperty());

            if (searchField == null) return;
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
                try {
                    fetchJobs(newValue.isEmpty() ? null : newValue);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchJobs() throws URISyntaxException {
        fetchJobs(null);
    }

    public void fetchJobs(String search) throws URISyntaxException {
        var uriString = BackendURIs.getJobs();
        if (search != null) {
            uriString += "/" + search;
        }

        var req = HttpRequest.newBuilder()
                .uri(new URI(uriString))
                .GET()
                .build();
        System.out.println(req);

        try (HttpClient http = HttpClient.newBuilder().build()) {
            http.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
                try {
                    Job[] j = gson.fromJson(response.body(), Job[].class);
                    Platform.runLater(() -> {
                        scrollPaneVBox.getChildren().clear();

                        for (Job job : j) {
                            createElement(job.getJobName(), job.getJobLocation(), job.getJobDescription(), job.getEmploymentType(), job.getEMailAddress());
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }).handle((a, e) -> {
                if (e == null) return this;
                // Hier könntent ihr in Platform.runLater einen errpr anzeigen
                Platform.runLater(() -> {

                    // Hier macht ihr ui updates
                });
                return this;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void postJob(String jobName, String jobDescription, String jobLocation, String employmentType, String eMail) throws IOException, InterruptedException {
        Job job = new Job(jobName, jobDescription, jobLocation, employmentType, false,eMail);
        System.out.println(gson.toJson(job));
        HttpRequest req = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(job)))
                .uri(URI.create("http://localhost:8080/addJob"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println(resp.statusCode());
    }

    //not working
    public void getJob(String search) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/job/" + search))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        Job[] j = gson.fromJson(resp.body(), Job[].class);
        Platform.runLater(() -> {
            jobName.setText(j[0].getJobName());
            System.out.println(j[0].getJobLocation());
        });
        System.out.println(resp.statusCode());
        System.out.println(resp.body());
    }


    private Scene scene;
    private Stage stage;
    private Parent root;

    public void switchToScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-job.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    public void addJob(ActionEvent event) throws IOException, InterruptedException {
        postJob(jobName.getText(), jobLocation.getText(), jobDescription.getText(), jobEmploymentType.getText(), jobEMail.getText());
        switchToMainScene(event);
        createElement(jobName.getText(), jobLocation.getText(), jobDescription.getText(), jobEmploymentType.getText(), jobEMail.getText());
    }

    //not working
    @FXML
    public void searchJob(String search) throws IOException, InterruptedException {
        // getJob(searchField.toString());
    }

    public void createElement(String jobName, String jobDescription, String jobLocation, String employmentType, String eMail) {
        Job job = new Job(jobName, jobDescription, jobLocation, employmentType, false,eMail);
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");
        card.prefWidthProperty().bind(scrollPaneVBox.widthProperty());
        Label head = new Label(job.getJobName());
        head.getStyleClass().add("head");
        Label location = new Label(job.getJobLocation());
        Label description = new Label(job.getJobDescription());
        Label employment = new Label(job.getEmploymentType());
        Label email = new Label(eMail);
        card.getChildren().addAll(head, location, description, employment, email);
        scrollPaneVBox.getChildren().addAll(card);
    }


}