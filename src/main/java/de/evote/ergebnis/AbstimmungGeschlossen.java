package de.evote.ergebnis;

import java.time.Instant;

public record AbstimmungGeschlossen(
        String abstimmungId,
        Instant zeitpunkt
) {
}