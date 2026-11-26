package de.evote.stimmabgabe.domain;

import java.time.Instant;

public class Stimmzettel {

    private final String id;
    private final String abstimmungId;
    private final String optionId;
    private final Instant abgegebenAm;

    public Stimmzettel(String id, String abstimmungId, String optionId, Instant abgegebenAm) {
        this.id = id;
        this.abstimmungId = abstimmungId;
        this.optionId = optionId;
        this.abgegebenAm = abgegebenAm;
    }

    public String getOptionId() {
        return optionId;
    }

    public String getAbstimmungId() {
        return abstimmungId;
    }
}
