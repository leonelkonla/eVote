
package de.eVote.ergebnis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import de.evote.ergebnis.ErgebnisBerechnungsService;
import de.evote.ergebnis.Abstimmungsergebnis;
import java.util.List;

public class ErgebnisBerechnungsServiceTest {

    @Test
    void testStimmenZaehlen() {

        ErgebnisBerechnungsService service =
                new ErgebnisBerechnungsService();

        int ergebnis = service.zaehleStimmen(
                java.util.List.of("A", "B", "A", "A"), "A");

        assertEquals(3, ergebnis);
    }

@Test
void testKeineStimmen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    int ergebnis = service.zaehleStimmen(
            java.util.List.of(), "A");

    assertEquals(0, ergebnis);
}

@Test
void testOptionOhneStimmen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    int ergebnis = service.zaehleStimmen(
            java.util.List.of("A", "B", "A"), "C");

    assertEquals(0, ergebnis);
}

@Test
void testProzentBerechnen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    double prozent = service.berechneProzent(3, 5);

    assertEquals(60.0, prozent, 0.001);
}

@Test
void testProzentOhneStimmen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    double prozent = service.berechneProzent(0, 0);

    assertEquals(0.0, prozent, 0.001);
}

@Test
void testAlleStimmenFuerEineOption() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    double prozent = service.berechneProzent(5, 5);

    assertEquals(100.0, prozent, 0.001);
}

@Test
void testAlleOptionenAuswerten() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    java.util.Map<String, Long> ergebnis =
            service.zaehleAlleOptionen(
                    java.util.List.of("A", "B", "A", "C", "A", "B"),
                    java.util.List.of("A", "B", "C"));

    assertEquals(3L, ergebnis.get("A"));
    assertEquals(2L, ergebnis.get("B"));
    assertEquals(1L, ergebnis.get("C"));
}

@Test
void testErgebnisMitProzentanteilen() {
    ErgebnisBerechnungsService service = new ErgebnisBerechnungsService();

    java.util.Map<String, Double> ergebnis =
        service.berechneAlleProzentanteile(
            java.util.List.of("A", "A", "B", "C"),
            java.util.List.of("A", "B", "C")
        );

    assertEquals(50.0, ergebnis.get("A"), 0.001);
    assertEquals(25.0, ergebnis.get("B"), 0.001);
    assertEquals(25.0, ergebnis.get("C"), 0.001);
}

@Test
void testAlleOptionenMitNullStimmen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    java.util.Map<String, Long> ergebnis =
            service.zaehleAlleOptionen(
                    java.util.List.of("A", "A", "B"),
                    java.util.List.of("A", "B", "C"));

    assertEquals(2L, ergebnis.get("A"));
    assertEquals(1L, ergebnis.get("B"));
    assertEquals(0L, ergebnis.get("C"));
}

@Test
void testProzentanteileOhneStimmen() {
    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    java.util.Map<String, Double> ergebnis =
            service.berechneAlleProzentanteile(
                    java.util.List.of(),
                    java.util.List.of("A", "B", "C"));

    assertEquals(0.0, ergebnis.get("A"), 0.001);
    assertEquals(0.0, ergebnis.get("B"), 0.001);
    assertEquals(0.0, ergebnis.get("C"), 0.001);
}

@Test
void testErstelleErgebnis() {

    ErgebnisBerechnungsService service =
            new ErgebnisBerechnungsService();

    List<String> stimmen =
            List.of("A", "A", "A", "B", "B");

    List<String> optionen =
            List.of("A", "B");

    Abstimmungsergebnis ergebnis =
            service.erstelleErgebnis(stimmen, optionen);

    ergebnis.schliesseAbstimmung();

    assertEquals(3L, ergebnis.getStimmenProOption().get("A"));
    assertEquals(2L, ergebnis.getStimmenProOption().get("B"));

    assertEquals(60.0, ergebnis.getProzentProOption().get("A"));
    assertEquals(40.0, ergebnis.getProzentProOption().get("B"));
}
}