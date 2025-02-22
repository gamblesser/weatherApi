package com.daniel.apis_lib.exeptions;

import java.io.IOException;

public class NotValidApiKeyException extends Exception {
    public NotValidApiKeyException() {
        super("Not a valid api key");
    }
}
