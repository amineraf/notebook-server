package com.oracle.training.model;

public class ScriptResponse {
    String result;
    String error;

    public ScriptResponse(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public ScriptResponse() {
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
