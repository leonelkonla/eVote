package de.evote.stimmabgabe.domain;

import java.time.Instant;

public final class Abstimmungszeitraum {

    private final Instant beginn;
    private final Instant ende;

    public Abstimmungszeitraum(Instant beginn, Instant ende) {
        this.beginn = beginn;
        this.ende = ende;
    }

    // Beginn gehört zum Zeitraum, Ende nicht
    public boolean enthaelt(Instant zeitpunkt) {
        return !zeitpunkt.isBefore(beginn) && zeitpunkt.isBefore(ende);
    }
}
