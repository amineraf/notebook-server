package com.oracle.proof.model;

public enum AvailableLang {
    PYTHON("python"),
    JAVA_SCRIPT("js");

    private String name;
    AvailableLang(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AvailableLang getInterpreterFromLanguageName(String language) {
        for (AvailableLang interpreter : AvailableLang.values()) {
            if (interpreter.name.equalsIgnoreCase(language)) {
                return interpreter;
            }
        }

        return null; // add default ?
    }
}
