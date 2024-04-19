package org.example.jobportal;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controller {
    private Gson gson = new Gson();

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
    private Label name;
    @FXML
    private TextField searchField;

    private String searchbarOut = "IT";
    //Get Job Infos from DB where searchbar is like jobName
    private String searchBar = "";

    @FXML
    public void initialize() {

        // displayJobName();
        try {
            fetchJobs();
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
                    System.out.println("the jobb be");
                    System.out.println(Arrays.toString(j));

                    Platform.runLater(() -> {

                        // Hier macht ihr ui updates
                       name.setText(j[0].jobName);
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

    public void putJob() throws URISyntaxException, ExecutionException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"action\":\"hello\"}"))
                .build();

        CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response = future.get();
        assert(response.statusCode() == 200);
        assert(response.body()).equals("{\"message\":\"ok\"}");
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

    public void addJob(ActionEvent event) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        putJob();
        switchToMainScene(event);
        createElement(jobName.getText(), jobLocation.getText(), jobDescription.getText(), jobEmploymentType.getText());
    }

    public void createElement(String jobName, String jobDescription, String jobLocation, String employmentType){
        Job job = new Job(1, jobName, jobDescription, jobLocation, employmentType, false);
        VBox card = new VBox();
        card.getStyleClass().add("card");
        card.getChildren().addAll(new Label(job.getJobName()), new Label(job.getJobLocation()), new Label(job.getJobShortDescription()), new Label(job.getEmploymentType()));
        scrollPane.setContent(card);

    }


}