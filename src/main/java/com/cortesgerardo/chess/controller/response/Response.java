package com.cortesgerardo.chess.controller.response;

public class Response {

    private Boolean success;

    public Response(final Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

}
