
package de.eVote.ergebnis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import de.evote.ergebnis.Abstimmungsergebnis;
import de.evote.ergebnis.AbstimmungGeschlossen;

public class AbstimmungsergebnisTest {

    @Test
    void testErgebnisSpeichern() {

        Map<String, Long> stimmen = Map.of(
                "A", 3L,
                "B", 2L
        );

        Map<String, Double> prozente = Map.of(
                "A", 60.0,
                "B", 40.0
        );

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        stimmen,
                        prozente
                );

        ergebnis.schliesseAbstimmung();

        assertEquals(3L, ergebnis.getStimmenProOption().get("A"));
        assertEquals(2L, ergebnis.getStimmenProOption().get("B"));
        assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
        assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
    }

    @Test
    void testErgebnisNichtFreigegeben() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        assertFalse(ergebnis.istAbstimmungGeschlossen());
    }

    @Test
    void testAbstimmungSchliessen() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        // Am Anfang ist die Abstimmung offen
        assertFalse(ergebnis.istAbstimmungGeschlossen());

        // Abstimmung schließen
        ergebnis.schliesseAbstimmung();

        // Jetzt muss sie geschlossen sein
        assertTrue(ergebnis.istAbstimmungGeschlossen());
    }

    @Test
    void testErgebnisVorAbschlussGesperrt() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getStimmenProOption()
        );
    }

    @Test
    void testProzentanteileVorAbschlussGesperrt() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getProzentProOption()
        );
    }

    @Test
    void testProzentanteileNachAbschlussSichtbar() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        ergebnis.schliesseAbstimmung();

        assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
        assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
    }

    @Test
    void testErgebnisfreigabeDurchDomainEvent() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        AbstimmungGeschlossen event =
                new AbstimmungGeschlossen(
                        "AB-001",
                        Instant.now()
                );

        ergebnis.verarbeiteAbstimmungGeschlossen(event);

        assertTrue(ergebnis.istAbstimmungGeschlossen());
        assertEquals(3L, ergebnis.getStimmenProOption().get("A"));
        assertEquals(60.0, ergebnis.getProzentProOption().get("A"), 0.001);
    }

    @Test
    void testFalschesDomainEventKeineFreigabe() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        AbstimmungGeschlossen event =
                new AbstimmungGeschlossen(
                        "AB-002",
                        Instant.now()
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> ergebnis.verarbeiteAbstimmungGeschlossen(event)
        );

        assertFalse(ergebnis.istAbstimmungGeschlossen());

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getStimmenProOption()
        );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getProzentProOption()
        );
    }
}
