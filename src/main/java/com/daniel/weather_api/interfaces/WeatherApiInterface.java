package com.daniel.weather_api.interfaces;

import com.daniel.weather_api.pojoClasses.WeatherObj;

public interface WeatherApiInterface {



    WeatherObj getAndCacheWeatherCity(String cityName) throws Exception;

}
