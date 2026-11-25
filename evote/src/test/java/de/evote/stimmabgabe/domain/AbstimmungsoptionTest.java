package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbstimmungsoptionTest {

    @Test
    void liefertIdUndText() {
        Abstimmungsoption option = new Abstimmungsoption("o1", "Ja");

        assertEquals("o1", option.getId());
        assertEquals("Ja", option.getText());
    }
}
