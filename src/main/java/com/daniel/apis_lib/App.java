package com.daniel.apis_lib;

import com.daniel.apis_lib.apis.weather_api.WeatherApi;
import com.daniel.apis_lib.apis.weather_api.enums.Behaviors;
import com.daniel.apis_lib.apis.weather_api.pojoClasses.WeatherObj;

public class App {
    public static void main(String[] args) {
        WeatherApi weatherApi = new WeatherApi("bb6ea02118db63bd5e4ba1a032889990", Behaviors.POLLING);



        WeatherObj w11 = weatherApi.getAndCacheWeatherCity("Kesfurva");
        WeatherObj w12 = weatherApi.getAndCacheWeatherCity("Kurasva");
        WeatherObj w13 = weatherApi.getAndCacheWeatherCity("Kuedva");
        WeatherObj w16 = weatherApi.getAndCacheWeatherCity("Kefurva");
        WeatherObj w18 = weatherApi.getAndCacheWeatherCity("Ksva");
        WeatherObj w19 = weatherApi.getAndCacheWeatherCity("Kdva");
        WeatherObj w45 = weatherApi.getAndCacheWeatherCity("paris");
        WeatherObj w14 = weatherApi.getAndCacheWeatherCity("Kurasasva");
        WeatherObj w1 = weatherApi.getAndCacheWeatherCity("Kuredva");
        WeatherObj w166 = weatherApi.getAndCacheWeatherCity("Kfvdedva");
        WeatherObj w3421 = weatherApi.getAndCacheWeatherCity("edva");

        WeatherObj w211 = weatherApi.getAndCacheWeatherCity("Kuredvdadawdaxa");
        WeatherObj w123166 = weatherApi.getAndCacheWeatherCity("Kfsawdaxvdedva");


        //System.out.println(weatherMoscow.main.temp_max);
        //System.out.println(.main.temp_max);
        System.out.println(weatherApi.getCities().size());



    }
}
