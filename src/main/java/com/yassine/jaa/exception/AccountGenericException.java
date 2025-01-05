package com.yassine.jaa.exception;

import org.springframework.http.HttpStatus;

public class AccountGenericException  extends RuntimeException {

        private final String message;
        private final HttpStatus httpStatusCode;


    public AccountGenericException(String message, HttpStatus httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


        @Override
        public String getMessage() {
            return message;
        }


        public int getHttpStatusCode() {
            return httpStatusCode.value();
        }
    }
