# How to Use the Weather API

The WeatherApi lets you get weather data for a city using a single method: getWeatherCity(). You’ll need an OpenWeatherMap API key to start.

## Getting Started
*Create a WeatherApi instance with your API key and a behavior mode:*
---

 WeatherApiInterface weatherApi = new WeatherApi(String apiKey,Behaviors behavior);

---
Use **ON_DEMAND** to fetch weather only when you ask.
Use **POLLING** to automatically refresh outdated cache data.

Call **getWeatherCity(String cityName)** with a city name (like "London") to get the weather.

## What You Get
A WeatherObj with weather details (temperature, conditions, etc.) if successful.
null if the city isn’t found or data isn’t available.

---
import com.daniel.weather_api.enums.Behaviors;
import com.daniel.weather_api.impl.WeatherApi;
import com.daniel.weather_api.interfaces.WeatherApiInterface;
import com.daniel.weather_api.pojoClasses.WeatherObj;
import com.daniel.weather_api.exeptions.NotValidApiKeyException;
import com.daniel.weather_api.exeptions.NotValidUrlParameterException;

public class WeatherApiExample {
    public static void main(String[] args) {
        // Step 1: Initialize the API with a real API key and ON_DEMAND mode
        WeatherApiInterface weatherApi = new WeatherApi("your-openweathermap-api-key", Behaviors.ON_DEMAND);

        // Example 1: Basic weather check for New York
        try {
            WeatherObj weather = weatherApi.getWeatherCity("New York");
            if (weather != null) {
                System.out.println("Temperature in New York: " + weather.getMain().getTemp() + "°C");
                System.out.println("Condition: " + weather.getWeather().get(0).getDescription());
            } else {
                System.out.println("Weather not available for New York.");
            }
        } catch (NotValidUrlParameterException e) {
            System.err.println("Invalid city name: " + e.getMessage());
        } catch (NotValidApiKeyException e) {
            System.err.println("Invalid API key: " + e.getMessage());
        }

        System.out.println("---");

        // Example 2: Reusing cached data for Tokyo (switch to POLLING)
        weatherApi = new WeatherApi("your-openweathermap-api-key", Behaviors.POLLING);
        try {
            // First call: Fetches and caches
            WeatherObj firstCall = weatherApi.getWeatherCity("Tokyo");
            System.out.println("First call - Tokyo temp: " + firstCall.getMain().getTemp() + "°C");

            // Second call: Should use cache if within 10 minutes
            WeatherObj secondCall = weatherApi.getWeatherCity("Tokyo");
            System.out.println("Second call - Tokyo temp: " + secondCall.getMain().getTemp() + "°C");
        } catch (NotValidUrlParameterException | NotValidApiKeyException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("---");

        // Example 3: Handling invalid city name
        try {
            weatherApi.getWeatherCity(""); // Empty city name
        } catch (NotValidUrlParameterException e) {
            System.err.println("Please enter a valid city name: " + e.getMessage());
        } catch (NotValidApiKeyException e) {
            System.err.println("API key issue: " + e.getMessage());
        }

        System.out.println("---");

        // Example 4: Handling invalid API key
        WeatherApiInterface badApi = new WeatherApi("wrong-key", Behaviors.ON_DEMAND);
        try {
            badApi.getWeatherCity("Moscow");
        } catch (NotValidApiKeyException e) {
            System.err.println("Check your API key—it’s not working: " + e.getMessage());
        } catch (NotValidUrlParameterException e) {
            System.err.println("Invalid city: " + e.getMessage());
        }

        System.out.println("---");

        // Example 5: Testing cache limit with multiple cities
        try {
            String[] cities = {"London", "Paris", "Berlin", "Rome", "Madrid", "Vienna", "Prague", "Warsaw", "Oslo", "Stockholm", "Sydney"};
            for (String city : cities) {
                WeatherObj weather = weatherApi.getWeatherCity(city);
                if (weather != null) {
                    System.out.println(city + " temp: " + weather.getMain().getTemp() + "°C");
                }
            }
            // After 10 cities, "London" should be removed from cache
            WeatherObj checkLondon = weatherApi.getWeatherCity("London");
            System.out.println("London after cache limit: " + (checkLondon != null ? "Fetched again" : "Not available"));
        } catch (NotValidUrlParameterException | NotValidApiKeyException e) {
            System.err.println("Error with cities: " + e.getMessage());
        }
    }
}
---

## What Can Go Wrong
Invalid City Name: If the city name is empty or null, you’ll get an error message like "Parameter cannot be null or empty".
Invalid API Key: If the key is wrong, you’ll see an error like "Invalid API key".
