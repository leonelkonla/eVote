
package de.evote.ergebnis;

import java.util.List;

public class ErgebnisBerechnungsService {

    public int zaehleStimmen(List<String> stimmen, String option) {
        return (int) stimmen.stream()
                .filter(stimme -> stimme.equals(option))
                .count();
    }

public double berechneProzent(int stimmenOption, int gesamtStimmen) {
    if (gesamtStimmen == 0) {
        return 0.0;
    }

    return (double) stimmenOption / gesamtStimmen * 100;
}

public java.util.Map<String, Long> zaehleAlleOptionen(
        List<String> stimmen,
        List<String> optionen) {

    java.util.Map<String, Long> ergebnis =
            new java.util.LinkedHashMap<>();

    for (String option : optionen) {
        long anzahl = stimmen.stream()
                .filter(stimme -> option.equals(stimme))
                .count();

        ergebnis.put(option, anzahl);
    }

    return ergebnis;
}

public java.util.Map<String, Double> berechneAlleProzentanteile(
        List<String> stimmen,
        List<String> optionen) {

    java.util.Map<String, Long> stimmenProOption =
            zaehleAlleOptionen(stimmen, optionen);

    java.util.Map<String, Double> prozentanteile =
            new java.util.LinkedHashMap<>();

    long gesamtStimmen = stimmenProOption.values()
            .stream()
            .mapToLong(Long::longValue)
            .sum();

    for (String option : optionen) {
        long anzahl = stimmenProOption.get(option);

        double prozent = berechneProzent(
                Math.toIntExact(anzahl),
                Math.toIntExact(gesamtStimmen));

        prozentanteile.put(option, prozent);
    }

    return prozentanteile;
}
}