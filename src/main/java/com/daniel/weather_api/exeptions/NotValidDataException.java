package com.daniel.weather_api.exeptions;

public class NotValidDataException extends Exception {
    public NotValidDataException(String message) {
        super(message);
    }
}
