package org.cobraparser.ssl;

public enum SslState {
    NONE("None"),
    VALID("Valid"),
    INVALID("Invalid");

    private String name;

    SslState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
