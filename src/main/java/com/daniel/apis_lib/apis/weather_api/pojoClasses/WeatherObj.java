package com.daniel.apis_lib.apis.weather_api.pojoClasses;


import java.util.ArrayList;



public class WeatherObj{
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    public static class Clouds{
        public int all;
    }

    public static class Coord{
        public double lon;
        public double lat;
    }

    public static class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
        public int sea_level;
        public int grnd_level;
    }
    public static class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Wind{
        public double speed;
        public int deg;
        public double gust;
    }


}

