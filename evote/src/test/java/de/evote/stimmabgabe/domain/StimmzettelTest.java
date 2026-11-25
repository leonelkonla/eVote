package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class StimmzettelTest {

    @Test
    void liefertAbstimmungIdUndOptionId() {
        Stimmzettel stimmzettel = new Stimmzettel("s1", "a1", "o1", Instant.parse("2026-12-15T12:00:00Z"));

        assertEquals("a1", stimmzettel.getAbstimmungId());
        assertEquals("o1", stimmzettel.getOptionId());
    }
}
