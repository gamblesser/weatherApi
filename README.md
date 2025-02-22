# How to Use the Weather API

The WeatherApi lets you get weather data for a city using a single method: getWeatherCity(). You’ll need an OpenWeatherMap API key to start.

## Getting Started
Create a WeatherApi instance with your API key and a behavior mode:
Use ON_DEMAND to fetch weather only when you ask.
Use POLLING to automatically refresh outdated cache data.

Call getWeatherCity() with a city name (like "London") to get the weather.

## What You Get
A WeatherObj with weather details (temperature, conditions, etc.) if successful.
null if the city isn’t found or data isn’t available.

## What Can Go Wrong
Invalid City Name: If the city name is empty or null, you’ll get an error message like "Parameter cannot be null or empty".
Invalid API Key: If the key is wrong, you’ll see an error like "Invalid API key".
