package de.evote.stimmabgabe.domain;

public class Abstimmungsoption {

    private final String id;
    private final String text;

    public Abstimmungsoption(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
