package com.oracle.proof.model;

public class Response {
    String result;
    String error;

    public Response(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public Response() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
