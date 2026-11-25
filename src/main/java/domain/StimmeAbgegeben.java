package domain;

import java.time.Instant;

public record StimmeAbgegeben(String abstimmungId, String optionId, Instant zeitpunkt) { }