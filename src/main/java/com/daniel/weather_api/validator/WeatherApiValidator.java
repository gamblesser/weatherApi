package com.daniel.weather_api.validator;

import com.daniel.weather_api.exeptions.NotValidApiKeyException;
import com.daniel.weather_api.exeptions.NotValidUrlParameterException;

import java.net.http.HttpResponse;

public class WeatherApiValidator {

    public void isValidStatusCodeResponse(HttpResponse<String> response) throws NotValidApiKeyException {

        if (response.statusCode()==401){
            throw new NotValidApiKeyException();
        }

    }

    public void isValidParameter(String parameter) throws NotValidUrlParameterException {

        if (!(parameter.matches("^[a-zA-Z0-9-._~%]*$"))){
            throw new NotValidUrlParameterException();
        }

    }
}
