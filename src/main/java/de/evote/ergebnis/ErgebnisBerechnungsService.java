
package de.evote.ergebnis;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ErgebnisBerechnungsService {

    public int zaehleStimmen(List<String> stimmen, String option) {

        return Math.toIntExact(stimmen.stream()
                .filter(stimme -> option.equals(stimme))
                .count());
    }

    public double berechneProzent(int stimmenOption, int gesamtStimmen) {

        if (gesamtStimmen == 0) {
            return 0.0;
        }

        return (double) stimmenOption / gesamtStimmen * 100;
    }

    public Map<String, Long> zaehleAlleOptionen(
            List<String> stimmen,
            List<String> optionen) {

        Map<String, Long> ergebnis = new LinkedHashMap<>();

        for (String option : optionen) {
            long anzahl = zaehleStimmen(stimmen, option);
            ergebnis.put(option, anzahl);
        }

        return ergebnis;
    }

    public Map<String, Double> berechneAlleProzentanteile(
            List<String> stimmen,
            List<String> optionen) {

        Map<String, Long> stimmenProOption =
                zaehleAlleOptionen(stimmen, optionen);

        return berechneProzentanteile(stimmenProOption);
    }

    private Map<String, Double> berechneProzentanteile(
            Map<String, Long> stimmenProOption) {

        Map<String, Double> prozentanteile =
                new LinkedHashMap<>();

        long gesamtStimmen = stimmenProOption.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        stimmenProOption.forEach((option, anzahl) -> {

            double prozent = berechneProzent(
                    Math.toIntExact(anzahl),
                    Math.toIntExact(gesamtStimmen));

            prozentanteile.put(option, prozent);
        });

        return prozentanteile;
    }

    public Abstimmungsergebnis erstelleErgebnis(
            List<String> stimmen,
            List<String> optionen) {

        Map<String, Long> stimmenProOption =
                zaehleAlleOptionen(stimmen, optionen);

        Map<String, Double> prozentProOption =
                berechneProzentanteile(stimmenProOption);

        return new Abstimmungsergebnis(
                stimmenProOption,
                prozentProOption);
    }
}
