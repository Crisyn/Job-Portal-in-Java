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

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Key;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    private TextField searchField;

    HttpClient httpClient = HttpClient.newBuilder().build();

    @FXML
    public void initialize() {
        // displayJobName();
        try {
            fetchJobs();
            scrollPaneVBox.prefWidthProperty().bind(scrollPane.widthProperty());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchJobs() throws URISyntaxException {
        var req = HttpRequest.newBuilder()
                .uri(new URI(BackendURIs.getJobs()))
                .GET()
                .build();

        try (HttpClient http = HttpClient.newBuilder().build()) {
            http.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
                try {
                    Job[] j = gson.fromJson(response.body(), Job[].class);
                    System.out.println(Arrays.toString(j));
                    System.out.println(j[0].getJobName());
                    Platform.runLater(() -> {
                        for (int i = 0; i < j.length; i++) {
                            createElement(j[i].getJobName(),j[i].getJobLocation(),j[i].getJobDescription(),j[i].getEmploymentType());
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

    public void postJob(String jobName, String jobDescription, String jobLocation, String employmentType) throws IOException, InterruptedException {
        Job job = new Job(jobName, jobDescription, jobLocation, employmentType, false);
        System.out.println(gson.toJson(job));
        HttpRequest req = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(job)))
                .uri(URI.create("http://localhost:8080/addJob"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println(resp.statusCode());
        System.out.println(resp.body());
    }

    //not working
    public void getJob(String searchBar) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/job/" + searchBar))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        Job[] j = gson.fromJson(resp.body(), Job[].class);
        Platform.runLater(() -> {
            jobName.setText(j[0].getJobName());
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
        postJob(jobName.getText(), jobLocation.getText(), jobDescription.getText(), jobEmploymentType.getText());
        switchToMainScene(event);
        createElement(jobName.getText(), jobLocation.getText(), jobDescription.getText(), jobEmploymentType.getText());
    }

    //not working
    @FXML
    public void searchJob(ActionEvent actionEvent, String searchField) throws IOException, InterruptedException {
        getJob(searchField);
    }

    public void createElement(String jobName, String jobDescription, String jobLocation, String employmentType) {
        Job job = new Job(jobName, jobDescription, jobLocation, employmentType, false);
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");
        card.prefWidthProperty().bind(scrollPaneVBox.widthProperty());
        Label head = new Label(job.getJobName());
        head.getStyleClass().add("head");
        Label location = new Label(job.getJobLocation());
        Label description = new Label(job.getJobDescription());
        Label employment = new Label(job.getEmploymentType());
        card.getChildren().addAll(head, location, description, employment);
        scrollPaneVBox.getChildren().addAll(card);
    }


}