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
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App extends Application {

    private Stage stage;
    private Scene scene;
    private VBox vbox;
    private HBox layer1;
    private HBox layer2;
    private HBox layer3;
    private Button randomizer;
    private Button imgButton;
    private Button quoteButton;
    private Button sizeButton;
    private Button fontSize;
    private ImageView imgView;
    private BorderPane border;

    public App() {
        stage = null;
        scene = null;
        vbox = new VBox();
        layer1 = new HBox();
        layer2 = new HBox();
        layer3 = new HBox();
        randomizer = new Button("Randomize All");
        imgButton = new Button("New Image");
        quoteButton = new Button("New Quote");
        fontSize = new Button("Font Size");
        sizeButton = new Button("Size Button");
        imgView = new ImageView();
        border = new BorderPane();
    } // App


    public void init() {
        // Top Layer Button
        
        vbox.getChildren().addAll(layer1,layer2, imgView, layer3);
        layer1.getChildren().addAll(fontSize, sizeButton);
        layer2.getChildren().addAll(imgButton, quoteButton);
        layer3.getChildren().addAll(randomizer);
        
        
        layer1.setHgrow(fontSize, Priority.ALWAYS);
        layer1.setHgrow(sizeButton, Priority.ALWAYS);
        layer1.setAlignment(Pos.BASELINE_CENTER);
        layer2.setAlignment(Pos.BASELINE_CENTER);
        layer3.setAlignment(Pos.BASELINE_CENTER);

        fontSize.setPrefSize(100, 35);
        sizeButton.setPrefSize(100, 35);
        imgButton.setPrefSize(100, 35);
        quoteButton.setPrefSize(100, 35);
        randomizer.setPrefSize(100, 35);

        // Image Layer
        Image defaultImage =  new Image("file:resources/sprites/cat_idle.gif");
        imgView.setImage(defaultImage); 
        imgView.setFitWidth(780);
        imgView.setFitHeight(1280);   

    } // init

    public void start(Stage stage) {
        Scene scene = new Scene(vbox);


        stage.setScene(scene);
        stage.setTitle("Wallpaper App");
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);

    } // start

    public void stop() {
    } // stop
} // App
