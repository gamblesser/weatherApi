package com.daniel.weather_api.exeptions;


public class NotValidApiKeyException extends NotValidDataException {
    public NotValidApiKeyException() {
        super("Not a valid api key");
    }
}
