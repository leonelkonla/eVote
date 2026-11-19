package domain;
import java.time.Instant;

public record Stimmezettel(String id, String abstimmungId, String optionId, Instant abgegebenAm) {

}
