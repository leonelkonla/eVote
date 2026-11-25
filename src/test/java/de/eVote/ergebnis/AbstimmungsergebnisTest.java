
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
import de.evote.ergebnis.ErgebnisVeroeffentlicht;

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
        ergebnis.veroeffentlicheErgebnis();

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
        assertFalse(ergebnis.istVeroeffentlicht());
    }

    @Test
    void testAbstimmungSchliessen() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        assertFalse(ergebnis.istAbstimmungGeschlossen());

        ergebnis.schliesseAbstimmung();

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
    void testProzentanteileNachVeroeffentlichungSichtbar() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        ergebnis.schliesseAbstimmung();
        ergebnis.veroeffentlicheErgebnis();

        assertTrue(ergebnis.istVeroeffentlicht());

        assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
        assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
    }

    @Test
    void testAbstimmungSchliessenDurchDomainEvent() {

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
        assertFalse(ergebnis.istVeroeffentlicht());

        ergebnis.veroeffentlicheErgebnis();

        assertTrue(ergebnis.istVeroeffentlicht());

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
        assertFalse(ergebnis.istVeroeffentlicht());

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getStimmenProOption()
        );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getProzentProOption()
        );
    }

    @Test
    void testGeschlosseneAbstimmungNochNichtVeroeffentlicht() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        ergebnis.schliesseAbstimmung();

        assertTrue(ergebnis.istAbstimmungGeschlossen());
        assertFalse(ergebnis.istVeroeffentlicht());

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getStimmenProOption()
        );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getProzentProOption()
        );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.getGesamtStimmen()
        );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.stimmenFuer("A")
        );
    }

    @Test
    void testVeroeffentlichungVorAbschlussNichtErlaubt() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        assertThrows(
                IllegalStateException.class,
                () -> ergebnis.veroeffentlicheErgebnis()
        );

        assertFalse(ergebnis.istVeroeffentlicht());
    }

    @Test
    void testGesamtStimmenUndStimmenProOption() {

        Abstimmungsergebnis ergebnis =
                new Abstimmungsergebnis(
                        "AB-001",
                        Map.of("A", 3L, "B", 2L),
                        Map.of("A", 60.0, "B", 40.0)
                );

        ergebnis.schliesseAbstimmung();
        ergebnis.veroeffentlicheErgebnis();

        assertTrue(ergebnis.istVeroeffentlicht());

        assertEquals(5L, ergebnis.getGesamtStimmen());
        assertEquals(3L, ergebnis.stimmenFuer("A"));
        assertEquals(2L, ergebnis.stimmenFuer("B"));
    }

@Test
void testDomainEventBeiVeroeffentlichung() {

    Abstimmungsergebnis ergebnis =
            new Abstimmungsergebnis(
                    "AB-001",
                    Map.of("A", 3L, "B", 2L),
                    Map.of("A", 60.0, "B", 40.0)
            );

    ergebnis.schliesseAbstimmung();

    ErgebnisVeroeffentlicht event =
            ergebnis.veroeffentlicheErgebnis();

    assertTrue(ergebnis.istVeroeffentlicht());

    assertEquals("AB-001", event.abstimmungId());

    assertTrue(event.zeitpunkt() != null);
}

@Test
void testMehrfacheVeroeffentlichungNichtErlaubt() {

    Abstimmungsergebnis ergebnis =
            new Abstimmungsergebnis(
                    "AB-001",
                    Map.of("A", 3L, "B", 2L),
                    Map.of("A", 60.0, "B", 40.0)
            );

    ergebnis.schliesseAbstimmung();
    ergebnis.veroeffentlicheErgebnis();

    assertThrows(
            IllegalStateException.class,
            () -> ergebnis.veroeffentlicheErgebnis()
    );

    assertTrue(ergebnis.istVeroeffentlicht());

    assertEquals(5L, ergebnis.getGesamtStimmen());
}
}
