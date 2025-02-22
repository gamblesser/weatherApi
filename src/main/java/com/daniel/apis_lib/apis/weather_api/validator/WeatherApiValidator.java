package com.daniel.apis_lib.apis.weather_api.validator;

import com.daniel.apis_lib.exeptions.NotValidApiKeyException;
import com.daniel.apis_lib.exeptions.NotValidUrlParameterException;

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
