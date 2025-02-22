package com.daniel.apis_lib.exeptions;

import java.io.IOException;

public class NotValidUrlParameterException extends IOException {
    public NotValidUrlParameterException() {
        super("Not a valid parameter");
    }
}
