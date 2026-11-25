package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BuergerIdTest {

    @Test
    void ungueltigeIdWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class, () -> new BuergerId("keine-uuid"));
    }
}
