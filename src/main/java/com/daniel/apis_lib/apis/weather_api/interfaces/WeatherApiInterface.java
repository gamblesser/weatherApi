package com.daniel.apis_lib.apis.weather_api.interfaces;

import com.daniel.apis_lib.apis.weather_api.pojoClasses.WeatherObj;

public interface WeatherApiInterface {



    WeatherObj getAndCacheWeatherCity(String cityName) throws Exception;

}
