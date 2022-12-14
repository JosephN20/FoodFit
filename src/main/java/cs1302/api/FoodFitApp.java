package cs1302.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.concurrent.Task;

import java.lang.Runnable;
import java.lang.Thread;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This is an App that determine how much calories
 * you should eat by getting your age, height, weight
 * gender, activity level, and goals. It will then provide
 * you with nine potential recipes you can make to reach
 * your calorie goal.
 */
public class FoodFitApp extends Application {

    private static final String CALORIE_API = "https://fitness-calculator.p.rapidapi.com/dailycalorie";
    private static final String RECIPE_API = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByNutrients";
    private static final String CONFIGPATH = "resources/config.properties";
    String recipeKey;
    String fitnessKey;

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private Stage stage;
    private Scene scene;
    private VBox vbox;
    private HBox layer1;
    private HBox layer2;
    private HBox layer3;
    private HBox layer4;
    private HBox layer5;
    private HBox layer6;
    private TextField ageBox;
    private ComboBox<String> genderBox;
    private TextField heightBox;
    private TextField weightBox;
    private ComboBox<String> activityLevel;
    private ComboBox<String> goal;
    private Button searchButton;
    private String uri;
    private ImageView[] imgViews;
    private TextFlow[] textFlows;
    private String[] recipeList;
    private String[] recipeImage;
    private Label label1;
    private Label label2;
    private ProgressBar progressBar;

    /**
     * Constructor a {@code FoodFitApp} object.
     */
    public FoodFitApp() {
        stage = null;
        scene = null;
        vbox = new VBox();
        layer1 = new HBox();
        layer2 = new HBox();
        layer3 = new HBox();
        layer4 = new HBox();
        layer5 = new HBox();
        layer6 = new HBox();
        ageBox = new TextField("Age");
        genderBox = new ComboBox<>();
        heightBox = new TextField("Height in cm");
        weightBox = new TextField("Weight in kg");
        activityLevel = new ComboBox<>();
        goal = new ComboBox<>();
        searchButton = new Button("Search");
        imgViews = new ImageView[9];
        for (int i = 0; i < imgViews.length; i++) {
            imgViews[i] = new ImageView();
        } // for
        textFlows = new TextFlow[9];
        for (int i = 0; i < textFlows.length; i++) {
            textFlows[i] = new TextFlow();
        } // for
        recipeList = new String[9];
        recipeImage = new String[9];
        label1 = new Label("Enter your age, height,weight, gender, activity level, and goal.");
        label2 = new Label("FoodFitApp provided by Spoonacular API and Fitness Calculator API");
        progressBar = new ProgressBar();

        // Setting API keys
        try (FileInputStream configFileStream = new FileInputStream(CONFIGPATH)) {
            Properties config = new Properties();
            config.load(configFileStream);
            recipeKey = config.getProperty("recipe.key");
            fitnessKey = config.getProperty("fitness.key");
        } catch (IOException ioe) {
            System.err.println(ioe);
            ioe.printStackTrace();
        } // try

    } // FoodFitApp


    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        // Combo Boxes
        genderBox.getItems().addAll("Male","Female");
        genderBox.setValue("Gender");

        activityLevel.getItems().addAll("1", "2", "3", "4", "5");
        activityLevel.setValue("Select Activity Level");

        goal.getItems().addAll("Maintain", "Gain", "Loss");
        goal.setValue("Pick Goal");

        // Layers
        vbox.getChildren().addAll(layer1, layer2, label1, layer3, layer4,layer5,layer6);
        layer1.getChildren().addAll(ageBox, heightBox, weightBox);
        layer2.getChildren().addAll(genderBox, activityLevel, goal, searchButton);
        layer3.getChildren().addAll(imgViews[0], textFlows[0], imgViews[1]);
        layer3.getChildren().addAll( textFlows[1], imgViews[2], textFlows[2]);
        layer4.getChildren().addAll(imgViews[3], textFlows[3], imgViews[4]);
        layer4.getChildren().addAll( textFlows[4], imgViews[5], textFlows[5]);
        layer5.getChildren().addAll(imgViews[6], textFlows[6], imgViews[7]);
        layer5.getChildren().addAll(textFlows[7], imgViews[8], textFlows[8]);
        layer6.getChildren().addAll(progressBar, label2);


        layer3.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");
        layer4.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");
        layer5.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");

        // progress bar
        progressBar.setProgress(0.0);
        progressBar.setPrefWidth(200);
        layer6.setSpacing(200);


        // Size and alignment
        ageBox.setPrefWidth(300);
        heightBox.setPrefWidth(300);
        weightBox.setPrefWidth(300);
        genderBox.setPrefWidth(225);
        activityLevel.setPrefWidth(225);
        goal.setPrefWidth(225);
        searchButton.setPrefWidth(225);

    } // init

    /**
     * {@inheritDoc}}
     */
    @Override
    public void start(Stage stage) {

        this.stage = stage;
        this.scene = new Scene(vbox);

        this.stage.setScene(scene);
        this.stage.setTitle("Wellness App");
        this.stage.sizeToScene();
        this.stage.show();
        this.stage.setWidth(900);
        this.stage.setHeight(820);
        setScene();
        Platform.runLater(() -> this.stage.setResizable(false));

        EventHandler<ActionEvent> handler = event ->  search();
        searchButton.setOnAction(e -> runNow(() -> {
            search();
        }));

    } // start

    /**
     * Method that gathers all the methods that are used.
     */
    public void search() {
        disable();
        startProcess();
        getCalorie();
        getRecipe();
        setFood();
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void stop() {
    } // stop

    /**
     * Method that uses a fitness calculator API
     * to return a String for calories based
     * on the goals that the user selects given the user
     * information.
     * @return calory
     */
    public String getCalorie() {
        try {
            String age = URLEncoder.encode(ageBox.getText(), StandardCharsets.UTF_8);
            String gender = URLEncoder.encode(genderBox.getValue().toString().toLowerCase(),
                StandardCharsets.UTF_8);
            String height = URLEncoder.encode(heightBox.getText(), StandardCharsets.UTF_8);
            String weight = URLEncoder.encode(weightBox.getText(), StandardCharsets.UTF_8);
            String activity = URLEncoder.encode("level_" + activityLevel.getValue().toString(),
                StandardCharsets.UTF_8);
            String query = String.format("?age=%s&gender=%s&height=%s&weight=%s&activitylevel=%s"
                , age, gender, height, weight, activity);
            uri = CALORIE_API + query;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("X-RapidAPI-Key", fitnessKey)
                .header("X-RapidAPI-Host", "fitness-calculator.p.rapidapi.com")
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonString = response.body();
            CalorieResponse calorieResponse = GSON.fromJson(jsonString, CalorieResponse.class);
            if (goal.getValue().toString() == "Maintain") {
                return calorieResponse.data.goals.maintainWeight.toString();
            } // if
            if (goal.getValue().toString() == "Gain") {
                return calorieResponse.data.goals.mildWeightGain.calory.toString();
            } // if
            if (goal.getValue().toString() == "Loss") {
                return calorieResponse.data.goals.mildWeightLoss.calory.toString();
            } // if
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        } // try

        return  null;

    } // getCalorie

    /**
     * Method that creates multiple string array
     * of potential recipes using spoonacular Recipe - Food -
     *  Nutrition API. The array contains titles and nutrients
     * in the recipe that the user can decide to make.
     * Along with the url of the image of the recipe.
     */
    public void getRecipe() {
        try {
            String maxCalories = URLEncoder.encode(Double.toString
                (Double.parseDouble(getCalorie()) / 4), StandardCharsets.UTF_8);
            String minCalories = URLEncoder.encode(Double.toString
                (Double.parseDouble(getCalorie()) / 4 - 200), StandardCharsets.UTF_8);
            String query = String.format("?minProtein=20&random=true&maxCalories"
                + "=%s&maxFat=25&minCalories=%s&number=9", maxCalories, minCalories);
            uri = RECIPE_API + query;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("X-RapidAPI-Key", recipeKey)
                .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonString = response.body();
            RecipeResponse[] recipeResponse = GSON.fromJson(jsonString, RecipeResponse[].class);

            for (int i = 0; i < recipeResponse.length; i++) {
            // Check if i is less than the length of the recipeList array
                recipeList[i] = recipeResponse[i].title + "\n " + recipeResponse[i].calories
                    + " calories\n " + recipeResponse[i].protein + " protein\n "
                    + recipeResponse[i].fat + " fat\n " + recipeResponse[i].carbs + " carbs\n";
                recipeImage[i] = recipeResponse[i].image;
                System.out.println(recipeImage[i]);
            }
        } catch (IOException | InterruptedException | ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        } // try
        Runnable mod2 = () ->  {
            enable();
        };
        Platform.runLater(mod2);
    } // getRecipe


    /**
     * Start the progress bar to indicate that
     * the app is working.
     */
    public void startProcess() {

        // Create a background Task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // Set the total number of steps in our process
                int steps = 3000;

                // Simulate a long running task
                for (int i = 0; i < steps; i++) {
                    // Update our progress and message properties
                    Thread.sleep(1);
                    updateProgress(i, steps);

                }
                return null;

            }
        };

        // Before starting our task, we need to bind our UI values to the properties on the task
        progressBar.progressProperty().bind(task.progressProperty());


        // Now, start the task on a background thread
        new Thread(task).start();
    }

    /**
     * Method that set the inital look of the app
     * using TextFlow and ImageViews.
     */
    public void setScene() {
        String DEF_IMG = "file:resources/grey.jpeg";
        for (int i = 0; i < imgViews.length; i++) {
            imgViews[i].setFitWidth(150);
            imgViews[i].setFitHeight(233);
            imgViews[i].setImage(new Image(DEF_IMG));
            textFlows[i].setPrefWidth(150);
            textFlows[i].setPrefHeight(233);
            textFlows[i].getChildren().add(new Text("Recipe and nutritions will be shown here."));


        } // for
    } // set

    /** Method that updates the app to have images of the
     * recipe and list out the some of the nutrition value
     * of the food.
    */
    public void setFood() {
        Runnable mod1 = () -> {
            label1.setText("You can eat " + getCalorie()
                + " calories. Here are some food options you can eat to reach it.");

            for (int i = 0; i < textFlows.length; i++) {
                textFlows[i].setPrefWidth(150);
                textFlows[i].setPrefHeight(233);
                textFlows[i].getChildren().clear();
                textFlows[i].getChildren().add(new Text(recipeList[i]));
                imgViews[i].setFitWidth(150);
                imgViews[i].setFitHeight(233);
                imgViews[i].setImage(new Image(recipeImage[i]));
            } // for
        }; // mod1
        Platform.runLater(mod1);
    } //  setFood

    /**
     * Method that disable
     * some buttons and textBoxes.
     */
    public void disable() {
        searchButton.setDisable(true);
        ageBox.setDisable(true);
        heightBox.setDisable(true);
        weightBox.setDisable(true);
        genderBox.setDisable(true);
        activityLevel.setDisable(true);
        goal.setDisable(true);
    } // disable

    /**
     * Method that enable
     * some buttons and textBoxes.
     */
    public void enable() {
        searchButton.setDisable(false);
        ageBox.setDisable(false);
        heightBox.setDisable(false);
        weightBox.setDisable(false);
        genderBox.setDisable(false);
        activityLevel.setDisable(false);
        goal.setDisable(false);
    } // enable

    /**
     * This method runs the target on a thread.
     *
     * @param target The target to be ran on a thread.
     */
    private void runNow(Runnable target) {
        Thread t = new Thread(target);
        t.setDaemon(true);
        t.start();
    }

} // FoodFitApp
