package org.hsbc.ledgerapi.commons.enums;

public enum AccountState {
    OPEN("Open"),
    CLOSED("Closed");

    private final String value;

    AccountState(String value) {
        this.value = value;
    }
}
