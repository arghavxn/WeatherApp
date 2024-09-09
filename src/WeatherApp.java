import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApp {
    private static final String API_KEY = "6c253335708fbfa32e290ac15dd5a5b3";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private static final String FORECAST_API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric";

    public static String getWeatherIconCode(String jsonData) {
        JSONObject json = new JSONObject(jsonData);
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        return weather.getString("icon");
    }

    public static void main(String[] args) {
        String userCity = getUserLocation(); // Get user location

        try {
            if (userCity != null) {
                String weatherData = getWeatherData(userCity);
                String displayData = parseWeatherData(weatherData);
                System.out.println(displayData);
            } else {
                System.out.println("Unable to determine user location.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    public static String getUserLocation() {
        try {
            URL url = new URL("https://ipinfo.io/json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            return json.getString("city");
        } catch (Exception e) {
            System.out.println("Error fetching location: " + e.getMessage());
        }
        return null;
    }
    public static String getWeatherData(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            URL url = new URL(String.format(API_URL, encodedCity, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());

            // Check if the "cod" field is "404" which indicates city not found
            if (json.has("cod") && json.getInt("cod") == 404) {
                return "City not found. Please enter a valid city name.";
            }

            return response.toString(); // Return valid weather data

        } catch (Exception e) {
            return "Error fetching weather data. Please try again.";
        }
    }


    public static String parseWeatherData(String jsonData) {
        JSONObject json = new JSONObject(jsonData);
        JSONObject main = json.getJSONObject("main");
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        JSONObject wind = json.getJSONObject("wind");
        JSONObject sys = json.getJSONObject("sys");

        String cityName = json.getString("name");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        String description = weather.getString("description");
        String iconCode = weather.getString("icon");
        double windSpeed = wind.getDouble("speed");
        long sunrise = sys.getLong("sunrise");
        long sunset = sys.getLong("sunset");

        return String.format(
                "Weather in %s:\nTemperature: %.1f°C\nHumidity: %d%%\nDescription: %s\nIcon Code: %s\nWind Speed: %.1f m/s\nSunrise: %s\nSunset: %s",
                cityName, temperature, humidity, description, iconCode, windSpeed,
                new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(sunrise * 1000)),
                new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(sunset * 1000))
        );
    }
}
