
package de.evote.ergebnis;

import java.util.Map;
import java.time.Instant;

public class Abstimmungsergebnis {

    private final String abstimmungId;
    private final Map<String, Long> stimmenProOption;
    private final Map<String, Double> prozentProOption;

    private boolean abstimmungGeschlossen = false;
    private boolean veroeffentlicht = false;

    public Abstimmungsergebnis(
            String abstimmungId,
            Map<String, Long> stimmenProOption,
            Map<String, Double> prozentProOption) {

        if (abstimmungId == null || abstimmungId.isBlank()) {
            throw new IllegalArgumentException(
                    "Die Abstimmungs-ID darf nicht leer sein."
            );
        }

        this.abstimmungId = abstimmungId;
        this.stimmenProOption = Map.copyOf(stimmenProOption);
        this.prozentProOption = Map.copyOf(prozentProOption);
    }

    public String getAbstimmungId() {
        return abstimmungId;
    }

    private void pruefeVeroeffentlichung() {

        if (!veroeffentlicht) {
            throw new IllegalStateException(
                    "Das Ergebnis wurde noch nicht veröffentlicht."
            );
        }
    }

    public Map<String, Long> getStimmenProOption() {
        pruefeVeroeffentlichung();
        return stimmenProOption;
    }

    public Map<String, Double> getProzentProOption() {
        pruefeVeroeffentlichung();
        return prozentProOption;
    }

    public long stimmenFuer(String optionId) {
        pruefeVeroeffentlichung();
        return stimmenProOption.getOrDefault(optionId, 0L);
    }

    public long getGesamtStimmen() {
        pruefeVeroeffentlichung();

        return stimmenProOption.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    public void schliesseAbstimmung() {
        this.abstimmungGeschlossen = true;
    }

    public boolean istAbstimmungGeschlossen() {
        return abstimmungGeschlossen;
    }

    public boolean istVeroeffentlicht() {
        return veroeffentlicht;
    }

    public ErgebnisVeroeffentlicht veroeffentlicheErgebnis() {

        if (!abstimmungGeschlossen) {
            throw new IllegalStateException(
                    "Die Abstimmung ist noch nicht geschlossen."
            );
        }

        if (veroeffentlicht) {
            throw new IllegalStateException(
                    "Das Ergebnis wurde bereits veröffentlicht."
            );
        }

        this.veroeffentlicht = true;

        return new ErgebnisVeroeffentlicht(
                abstimmungId,
                Instant.now()
        );
    }

    public void verarbeiteAbstimmungGeschlossen(
            AbstimmungGeschlossen event) {

        if (event == null) {
            throw new IllegalArgumentException(
                    "Das Event darf nicht null sein."
            );
        }

        if (!abstimmungId.equals(event.abstimmungId())) {
            throw new IllegalArgumentException(
                    "Das Event gehört zu einer anderen Abstimmung."
            );
        }

        schliesseAbstimmung();
    }
}