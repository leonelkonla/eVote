
package de.evote.ergebnis;

import java.util.Map;

public class Abstimmungsergebnis {

    private final String abstimmungId;
    private final Map<String, Long> stimmenProOption;
    private final Map<String, Double> prozentProOption;

    private boolean abstimmungGeschlossen = false;

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

    public Map<String, Long> getStimmenProOption() {

        if (!abstimmungGeschlossen) {
            throw new IllegalStateException(
                    "Die Abstimmung ist noch nicht geschlossen."
            );
        }

        return stimmenProOption;
    }

    public Map<String, Double> getProzentProOption() {

        if (!abstimmungGeschlossen) {
            throw new IllegalStateException(
                    "Die Abstimmung ist noch nicht geschlossen."
            );
        }

        return prozentProOption;
    }

    public void schliesseAbstimmung() {
        this.abstimmungGeschlossen = true;
    }

    public boolean istAbstimmungGeschlossen() {
        return abstimmungGeschlossen;
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