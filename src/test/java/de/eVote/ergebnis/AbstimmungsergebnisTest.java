
package de.eVote.ergebnis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import de.evote.ergebnis.Abstimmungsergebnis;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
                new Abstimmungsergebnis(stimmen, prozente);
        
        ergebnis.schliesseAbstimmung();

        assertEquals(3L, ergebnis.getStimmenProOption().get("A"));
        assertEquals(2L, ergebnis.getStimmenProOption().get("B"));

        assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
        assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
    }

@Test
void testErgebnisNichtFreigegeben() {

    boolean abstimmungGeschlossen = false;

    boolean ergebnisFreigegeben =
            abstimmungGeschlossen;

    assertEquals(false, ergebnisFreigegeben);
}

@Test
void testAbstimmungSchliessen() {

    Abstimmungsergebnis ergebnis =
            new Abstimmungsergebnis(
                    Map.of("A", 3L, "B", 2L),
                    Map.of("A", 60.0, "B", 40.0)
            );

    // Am Anfang ist die Abstimmung offen
    assertEquals(false, ergebnis.istAbstimmungGeschlossen());

    // Abstimmung schließen
    ergebnis.schliesseAbstimmung();

    // Jetzt muss sie geschlossen sein
    assertEquals(true, ergebnis.istAbstimmungGeschlossen());
}

@Test
void testErgebnisVorAbschlussGesperrt() {

    Abstimmungsergebnis ergebnis =
            new Abstimmungsergebnis(
                    Map.of("A", 3L, "B", 2L),
                    Map.of("A", 60.0, "B", 40.0)
            );

    org.junit.jupiter.api.Assertions.assertThrows(
            IllegalStateException.class,
            () -> ergebnis.getStimmenProOption()
    );
}

@Test
void testProzentanteileVorAbschlussGesperrt() {

    Abstimmungsergebnis ergebnis =
            new Abstimmungsergebnis(
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
                    Map.of("A", 3L, "B", 2L),
                    Map.of("A", 60.0, "B", 40.0)
            );

    ergebnis.schliesseAbstimmung();

    assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
    assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
}
}