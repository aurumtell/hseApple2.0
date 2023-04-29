package com.hseapple.app.error;


public class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse() {
    }

    public String getMessage() {
        return error;
    }
}
