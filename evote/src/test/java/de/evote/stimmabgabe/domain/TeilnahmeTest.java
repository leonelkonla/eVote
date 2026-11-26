package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class TeilnahmeTest {

    @Test
    void liefertBuergerIdUndAbstimmungId() {
        Teilnahme teilnahme = new Teilnahme("b1", "a1", Instant.parse("2026-12-15T12:00:00Z"));

        assertEquals("b1", teilnahme.getBuergerId());
        assertEquals("a1", teilnahme.getAbstimmungId());
    }
}
