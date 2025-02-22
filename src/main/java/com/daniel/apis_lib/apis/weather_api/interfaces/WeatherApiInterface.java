package com.daniel.apis_lib.apis.weather_api.interfaces;

import com.daniel.apis_lib.apis.weather_api.signatures.WeatherObj;
import org.json.JSONObject;

import java.util.Map;

public interface WeatherApiInterface {



    WeatherObj getAndCacheWeatherCity(String cityName) throws Exception;

}
