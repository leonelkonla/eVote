package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class AbstimmungszeitraumTest {

    private static final Instant BEGINN = Instant.parse("2026-12-01T00:00:00Z");
    private static final Instant ENDE = Instant.parse("2026-12-31T00:00:00Z");

    private final Abstimmungszeitraum zeitraum = new Abstimmungszeitraum(BEGINN, ENDE);

    @Test
    void zeitpunktInnerhalbWirdEnthalten() {
        assertTrue(zeitraum.enthaelt(Instant.parse("2026-12-15T12:00:00Z")));
    }

    @Test
    void beginnGehoertZumZeitraum() {
        assertTrue(zeitraum.enthaelt(BEGINN));
    }

    @Test
    void endeGehoertNichtZumZeitraum() {
        assertFalse(zeitraum.enthaelt(ENDE));
    }

    @Test
    void zeitpunktVorBeginnWirdNichtEnthalten() {
        assertFalse(zeitraum.enthaelt(Instant.parse("2026-11-30T23:59:59Z")));
    }
}
