package com.yassine.jaa.model;

public enum TransactionTypes {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW"),

    ;

    private final String displayName;

    TransactionTypes(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TransactionTypes fromString(String value) {
        for (TransactionTypes type : TransactionTypes.values()) {
            if (type.displayName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TransactionTypes: " + value);
    }
}
