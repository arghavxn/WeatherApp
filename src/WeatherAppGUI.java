import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

public class WeatherAppGUI extends Application {
    private TextField cityInput;
    private Label resultLabel;
    private ImageView weatherIcon;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        // Create a background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.jpeg"));
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        Label titleLabel = new Label("Weather Forecast");
        titleLabel.getStyleClass().add("title-label");

        Label instructionLabel = new Label("Enter city name:");
        cityInput = new TextField();
        Button submitButton = new Button("Get Weather");
        submitButton.getStyleClass().add("submit-button");

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(cityInput, submitButton);

        weatherIcon = new ImageView();
        weatherIcon.setFitWidth(100);
        weatherIcon.setFitHeight(100);

        resultLabel = new Label();
        resultLabel.setWrapText(true);
        resultLabel.getStyleClass().add("result-label");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(background));
        layout.getChildren().addAll(titleLabel, instructionLabel, inputBox, weatherIcon, resultLabel);

        submitButton.setOnAction(e -> getWeatherData());

        Scene scene = new Scene(layout, 400, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Automatically get weather data for user's location
        String userCity = WeatherApp.getUserLocation();
        if (userCity != null) {
            cityInput.setText(userCity);
            getWeatherData();
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getWeatherData() {
        String city = cityInput.getText();
        String weatherData = WeatherApp.getWeatherData(city);

        if (weatherData.contains("City not found") || weatherData.contains("Error fetching")) {
            resultLabel.setText(weatherData); // Display the error message
            weatherIcon.setImage(null); // Clear the icon if any
        } else {
            String displayData = WeatherApp.parseWeatherData(weatherData);
            resultLabel.setText(displayData);

            // Extract the icon code using a separate method
            String iconCode = WeatherApp.getWeatherIconCode(weatherData);

            // Update weather icon based on weather condition
            Image weatherImage = new Image(getClass().getResourceAsStream("/icons/" + iconCode + ".png"));
            weatherIcon.setImage(weatherImage);
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}