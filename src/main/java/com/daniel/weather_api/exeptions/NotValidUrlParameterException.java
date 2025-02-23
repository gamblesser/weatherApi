package com.daniel.weather_api.exeptions;

import java.io.IOException;

public class NotValidUrlParameterException extends NotValidDataException {
    public NotValidUrlParameterException() {
        super("Not a valid parameter");
    }
}
