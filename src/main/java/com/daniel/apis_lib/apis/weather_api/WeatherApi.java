package com.daniel.apis_lib.apis.weather_api;

import com.daniel.apis_lib.apis.weather_api.enums.Behaviors;
import com.daniel.apis_lib.apis.weather_api.interfaces.WeatherApiInterface;
import com.daniel.apis_lib.apis.weather_api.signatures.City;
import com.daniel.apis_lib.apis.weather_api.signatures.WeatherObj;
import com.daniel.apis_lib.apis.weather_api.validator.WeatherApiValidator;
import com.daniel.apis_lib.exeptions.NotValidApiKeyException;
import com.daniel.apis_lib.exeptions.NotValidUrlParameterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;


public class WeatherApi implements WeatherApiInterface {

    final public static ArrayList<WeatherApi> weatherApiCache = new ArrayList<>();
    private final List<City> cities = Collections.synchronizedList(new ArrayList<>());
    final private WeatherApiValidator validator = new WeatherApiValidator();
    private String apiKey;
    private final HttpClient client;
    private final Behaviors behavior;



    public static void deleteweatherApiCacheObj(WeatherApi weatherApi) {
        weatherApiCache.remove(weatherApi);
    }


    public WeatherApi(String apiKey, Behaviors behavior) throws RuntimeException {

        for (WeatherApi weatherApi : weatherApiCache) {
            if (weatherApi.getApiKey().equals(apiKey)) {
                throw new RuntimeException("Api key already exists");
            }
        }
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.behavior = behavior;
        weatherApiCache.add(this);
    }




    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<City> getCities() {
        return cities;
    }

    public void removeCityByName(String cityName) {
        cities.removeIf((city) -> Objects.equals(city.getCityName(),cityName));
    }



    public Map<String, Double> getCityLonLat(String cityName) {
        try {
            validator.isValidParameter(cityName);
        } catch (NotValidUrlParameterException e) {
            throw new RuntimeException(e);
        }

        String urlString = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", cityName, apiKey);

        HttpResponse<String> response = null;
        try {
            response = sendRequest(urlString);

        }  catch (InterruptedException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            validator.isValidStatusCodeResponse(response);
        } catch (NotValidApiKeyException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonCity = null;
        try {
            JSONArray jsonArray = new JSONArray(response.body());
            if (jsonArray.isEmpty()){
                return new HashMap<String,Double>();
            }
            jsonCity = (JSONObject) jsonArray.get(0);

        } catch (JSONException err) {
             throw new RuntimeException(err);
        }

        double lon = Double.parseDouble(jsonCity.get("lon").toString());
        double lat = Double.parseDouble(jsonCity.get("lat").toString());

        Map<String, Double> lonLat = new HashMap<String, Double>();

        lonLat.put("lon", lon);
        lonLat.put("lat", lat);

        return lonLat;

    }


    public WeatherObj getAndCacheWeatherCity(String cityName) {
        WeatherObj weatherObj =null;
        Double lon = null;
        Double lat = null;


        try {
            validator.isValidParameter(cityName);
        } catch (NotValidUrlParameterException e) {
            throw new RuntimeException(e);
        }


        if (behavior == Behaviors.POLLING) {
            updateCitiesCache();
        }

        Long timeBetween = null;
        for (City cityObj : cities) {

            timeBetween = Duration.between(LocalDateTime.now(), cityObj.getCreatedDateTime()).toMinutes();

            if (cityObj.getCityName().equals(cityName) && timeBetween < 10) {
                return cityObj.getWeatherObj();
            }

        }

        Map<String, Double> lonLat = getCityLonLat(cityName);
        if (!lonLat.isEmpty()){

            lon = lonLat.get("lon");
            lat = lonLat.get("lat");


        }


        for (City cityObj : cities) {
            timeBetween = Duration.between(LocalDateTime.now(), cityObj.getCreatedDateTime()).toMinutes();
            if (Objects.equals(cityObj.getLon(), lon) && Objects.equals(cityObj.getLat(), lat) && Objects.equals(cityObj.getCityName(),cityName) && timeBetween < 10) {
                return cityObj.getWeatherObj();
            }

        }


        if (!lonLat.isEmpty()) {
            weatherObj = sendRequestToGetWeatherCityJSON(cityName, lat, lon);
        }

        if ((cities.size() > 9)) {

            cities.remove(0);

        }
        cities.add(new City(cityName, lat, lon, weatherObj,LocalDateTime.now()));




        return weatherObj;
    }

    private HttpResponse<String> sendRequest(String urlString) throws URISyntaxException, IOException, InterruptedException {
        URI uri = null;

        uri = new URI(urlString);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = null;


        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    private WeatherObj sendRequestToGetWeatherCityJSON(String cityName, Double lat, Double lon) {
        try {
            validator.isValidParameter(cityName);
        } catch (NotValidUrlParameterException e) {
            throw new RuntimeException(e);
        }


        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", lat, lon, apiKey);

        HttpResponse<String> response = null;

        try {
            response = sendRequest(urlString);


        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            validator.isValidStatusCodeResponse(response);
        } catch (NotValidApiKeyException e) {
            throw new RuntimeException(e);
        }

        //JSONObject weatherObj = null;
        WeatherObj weatherObj = null;
        //weatherObj = new JSONObject(response.body());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            weatherObj = mapper.readValue(response.body(), WeatherObj.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return weatherObj;
    }

    private void updateCitiesCache() {

        WeatherObj weatherCityJSON = null;
        for (City cityObj : cities) {
            String cityName = cityObj.getCityName();
            Double lat = cityObj.getLat();
            Double lon = cityObj.getLon();
            LocalDateTime localDateTime =  LocalDateTime.now();
            if (Duration.between(localDateTime,cityObj.getCreatedDateTime()).toMinutes() > 10) {
                try {
                    weatherCityJSON = sendRequestToGetWeatherCityJSON(cityName, lat, lon);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                cityObj.setCreatedDateTime(localDateTime);
                cityObj.setWeatherObj(weatherCityJSON);
            }

        }
    }


}
