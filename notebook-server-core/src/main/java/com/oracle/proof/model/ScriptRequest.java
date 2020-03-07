package com.oracle.proof.model;

import javax.validation.constraints.NotNull;

public class ScriptRequest {
    @NotNull(message = "Code cannot be null")
    private String code;
    private String sessionId;
    private String language;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}