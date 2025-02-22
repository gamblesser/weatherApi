package com.daniel.apis_lib.apis.weather_api.signatures;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class City {
    private String cityName;
    private Double lat;
    private Double lon;
    //private JSONObject weatherObj;
    private WeatherObj weatherObj;
    private LocalDateTime createdDateTime;


    City() {

    }

    public City(String cityName, Double lat, Double lon, WeatherObj weatherObj,LocalDateTime createdDateTime) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
        this.weatherObj = weatherObj;
        this.createdDateTime = createdDateTime;


    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public WeatherObj getWeatherObj() {
        return weatherObj;
    }

    public void setWeatherObj(WeatherObj weatherObj) {
        this.weatherObj = weatherObj;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
