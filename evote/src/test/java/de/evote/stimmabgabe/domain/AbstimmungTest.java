package de.evote.stimmabgabe.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class AbstimmungTest {

    private static final Instant BEGINN = Instant.parse("2026-12-01T00:00:00Z");
    private static final Instant ENDE = Instant.parse("2026-12-31T00:00:00Z");
    private static final Instant IM_ZEITRAUM = Instant.parse("2026-12-15T12:00:00Z");
    private static final Instant NACH_ENDE = Instant.parse("2027-01-01T00:00:00Z");

    private final Abstimmung abstimmung = new Abstimmung("a1", "Neuer Spielplatz",
            "Soll im Stadtpark ein Spielplatz gebaut werden?", new Abstimmungszeitraum(BEGINN, ENDE));

    private void zweiOptionenHinzufuegen() {
        abstimmung.optionHinzufuegen(new Abstimmungsoption("ja", "Ja"));
        abstimmung.optionHinzufuegen(new Abstimmungsoption("nein", "Nein"));
    }

    @Test
    void neueAbstimmungIstNichtAktiv() {
        assertFalse(abstimmung.istAktiv(IM_ZEITRAUM));
    }

    @Test
    void startenMitWenigerAlsZweiOptionenWirdAbgelehnt() {
        abstimmung.optionHinzufuegen(new Abstimmungsoption("ja", "Ja"));

        assertThrows(IllegalStateException.class, abstimmung::starten);
    }

    @Test
    void gestarteteAbstimmungIstImZeitraumAktiv() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertTrue(abstimmung.istAktiv(IM_ZEITRAUM));
    }

    @Test
    void gestarteteAbstimmungIstAusserhalbDesZeitraumsNichtAktiv() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertFalse(abstimmung.istAktiv(NACH_ENDE));
    }

    @Test
    void geschlosseneAbstimmungIstNichtAktiv() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();
        abstimmung.schliessen();

        assertFalse(abstimmung.istAktiv(IM_ZEITRAUM));
    }

    @Test
    void schliessenOhneStartWirdAbgelehnt() {
        assertThrows(IllegalStateException.class, abstimmung::schliessen);
    }

    @Test
    void optionHinzufuegenNachStartWirdAbgelehnt() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertThrows(IllegalStateException.class,
                () -> abstimmung.optionHinzufuegen(new Abstimmungsoption("vielleicht", "Vielleicht")));
    }

    @Test
    void stimmabgabeFuerVorhandeneOptionImZeitraumIstGueltig() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertTrue(abstimmung.pruefeStimmabgabe("ja", IM_ZEITRAUM));
    }

    @Test
    void stimmabgabeFuerUnbekannteOptionIstUngueltig() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertFalse(abstimmung.pruefeStimmabgabe("vielleicht", IM_ZEITRAUM));
    }

    @Test
    void stimmabgabeVorDemStartIstUngueltig() {
        zweiOptionenHinzufuegen();

        assertFalse(abstimmung.pruefeStimmabgabe("ja", IM_ZEITRAUM));
    }

    @Test
    void stimmabgabeNachDemEndeIstUngueltig() {
        zweiOptionenHinzufuegen();
        abstimmung.starten();

        assertFalse(abstimmung.pruefeStimmabgabe("ja", NACH_ENDE));
    }
}
