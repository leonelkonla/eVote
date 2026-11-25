package de.evote.stimmabgabe.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Abstimmung {

    private final String id;
    private final String titel;
    private final String beschreibung;
    private Abstimmungsstatus status = Abstimmungsstatus.ENTWURF;
    private final Abstimmungszeitraum zeitraum;
    private final List<Abstimmungsoption> optionen = new ArrayList<>();

    public Abstimmung(String id, String titel, String beschreibung, Abstimmungszeitraum zeitraum) {
        this.id = id;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.zeitraum = zeitraum;
    }

    public void optionHinzufuegen(Abstimmungsoption option) {
        if (status != Abstimmungsstatus.ENTWURF) {
            throw new IllegalStateException("Optionen können nur im Entwurf hinzugefügt werden");
        }
        optionen.add(option);
    }

    // Multiplizität 2..* im UML: ohne mindestens zwei Optionen kein Start
    public void starten() {
        if (status != Abstimmungsstatus.ENTWURF) {
            throw new IllegalStateException("Nur ein Entwurf kann gestartet werden");
        }
        if (optionen.size() < 2) {
            throw new IllegalStateException("Mindestens 2 Optionen erforderlich");
        }
        status = Abstimmungsstatus.AKTIV;
    }

    public void schliessen() {
        if (status != Abstimmungsstatus.AKTIV) {
            throw new IllegalStateException("Nur eine aktive Abstimmung kann geschlossen werden");
        }
        status = Abstimmungsstatus.GESCHLOSSEN;
    }

    public boolean pruefeStimmabgabe(String optionId, Instant zeitpunkt) {
        return istAktiv(zeitpunkt) && optionen.stream().anyMatch(o -> o.getId().equals(optionId));
    }

    public boolean istAktiv(Instant zeitpunkt) {
        return status == Abstimmungsstatus.AKTIV && zeitraum.enthaelt(zeitpunkt);
    }
}
