# Weather App

## Description
This Weather App is a Java application with a JavaFX GUI that allows users to fetch and display current weather information for a specified city. It utilizes the OpenWeatherMap API to retrieve real-time weather data.

## Features
- User-friendly graphical interface
- Real-time weather data retrieval
- Display of temperature, humidity, and weather description
- Support for searching weather by city name

## Prerequisites
- Java Development Kit (JDK) 11 or later
- JavaFX SDK 22.0.2 or later
- IntelliJ IDEA (recommended IDE)
- Active internet connection for API calls

## Setup and Installation
1. Clone the repository or download the source code.
2. Open the project in IntelliJ IDEA.
3. Set up JavaFX:
   - Download JavaFX SDK from [openjfx.io](https://openjfx.io/)
   - Extract the SDK to a known location on your computer
   - In IntelliJ IDEA, go to File > Project Structure > Libraries
   - Add the JavaFX SDK library by selecting the 'lib' folder from the extracted SDK
4. Configure VM options:
   - Go to Run > Edit Configurations
   - Add the following VM options:
     ```
     --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```
   - Replace `/path/to/javafx-sdk` with the actual path to your JavaFX SDK

## Running the Application
1. Ensure all setup steps are completed.
2. Run the `WeatherAppGUI` class.
3. Enter a city name in the input field.
4. Click the "Get Weather" button to fetch and display weather information.

## Project Structure
- `WeatherApp.java`: Contains the core logic for API calls and data processing
- `WeatherAppGUI.java`: Implements the JavaFX GUI for the application

## API Key
This application uses the OpenWeatherMap API. You need to sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/api) and replace the placeholder in the `WeatherApp.java` file with your actual API key.

## Future Enhancements
- Add support for multiple cities
- Implement weather forecasts
- Include more detailed weather information
- Add visual representations of weather conditions

## Contributing
Contributions to improve the app are welcome. Please feel free to fork the repository and submit pull requests.

## License
This project is available under the MIT License.
