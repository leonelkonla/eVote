
package de.evote.ergebnis;

import java.time.Instant;

public record ErgebnisVeroeffentlicht(
        String abstimmungId,
        Instant zeitpunkt
) {
}