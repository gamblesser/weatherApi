package com.daniel.weather_api.interfaces;

import com.daniel.weather_api.exeptions.NotValidApiKeyException;
import com.daniel.weather_api.exeptions.NotValidUrlParameterException;
import com.daniel.weather_api.pojoClasses.WeatherObj;

public interface WeatherApiInterface {



    WeatherObj getWeatherCity(String cityName) throws NotValidUrlParameterException, NotValidApiKeyException;

}
