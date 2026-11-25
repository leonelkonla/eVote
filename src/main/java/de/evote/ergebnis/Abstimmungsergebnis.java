
package de.evote.ergebnis;

import java.util.Map;

public class Abstimmungsergebnis {

    private final Map<String, Long> stimmenProOption;
    private final Map<String, Double> prozentProOption;
    private boolean abstimmungGeschlossen = false;

    public Abstimmungsergebnis(
            Map<String, Long> stimmenProOption,
            Map<String, Double> prozentProOption) {

        this.stimmenProOption = Map.copyOf(stimmenProOption);
        this.prozentProOption = Map.copyOf(prozentProOption);
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
}