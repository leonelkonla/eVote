package de.evote.stimmabgabe.domain;

import java.time.Instant;

public class Teilnahme {

    private final String buergerId;
    private final String abstimmungId;
    private final Instant teilgenommenAm;

    public Teilnahme(String buergerId, String abstimmungId, Instant teilgenommenAm) {
        this.buergerId = buergerId;
        this.abstimmungId = abstimmungId;
        this.teilgenommenAm = teilgenommenAm;
    }

    public String getBuergerId() {
        return buergerId;
    }

    public String getAbstimmungId() {
        return abstimmungId;
    }
}
