package de.evote.stimmabgabe.domain;

import java.util.regex.Pattern;

public record BuergerId(String value) {

    private static final Pattern UUID = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public BuergerId {
        if (value == null || !UUID.matcher(value).matches()) {
            throw new IllegalArgumentException("Ungültige BuergerId: " + value);
        }
    }
}
