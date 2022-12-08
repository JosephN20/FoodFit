package cs1302.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App extends Application {

    private Stage stage;
    private Scene scene;
    private VBox vbox;
    private HBox layer1;
    private HBox layer2;
    private Button randomizer;
    private Button imgButton;
    private Button quoteButton;
    private Button sizeButton;
    private Button fontSize;

    public App() {
        stage = null;
        scene = null;
        vbox = new VBox();
        layer1 = new HBox();
        layer2 = new HBox();
        randomizer = new Button("Randomize All");
        imgButton = new Button("New Image");
        quoteButton = new Button("New Quote");
        fontSize = new Button("Font Size");
        sizeButton = new Button("Size Button");

    } // App


    public void init() {
        // Top Layer Button
        vbox.getChildren().addAll(layer1,layer2);
        layer1.getChildren().addAll(randomizer, sizeButton);
        layer2.getChildren().addAll(imgButton, quoteButton, fontSize);


    } // init

    public void start(Stage stage) {
        Scene scene = new Scene(vbox);


        stage.setScene(scene);
        stage.setTitle("Wallpaper App");
        stage.sizeToScene();
        stage.show();

    } // start

    public void stop() {
    } // stop
} // App
