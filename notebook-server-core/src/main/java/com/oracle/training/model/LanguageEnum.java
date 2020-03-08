package com.oracle.training.model;

public enum LanguageEnum {

    PYTHON("python");
    // todo Adding , NEWLANGUAGE("NEWLANGUAGE");

    private String langName;

    LanguageEnum(String langName) {
        this.langName = langName;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
}
