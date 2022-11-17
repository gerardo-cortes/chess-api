package com.cortesgerardo.chess.controller.response;

public class ErrorResponse extends Response {
    private String message;

    public static ErrorResponse of(final String message) {
        return new ErrorResponse(message);
    }

    public ErrorResponse(final String message) {
        super(false);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
