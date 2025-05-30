package ies.sequeros.dam.spotydam.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reaction {
    private UUID userId;
    private LocalDate date;

    public Reaction(UUID userId, LocalDate date) {
        this.userId = Objects.requireNonNull(userId);
        this.date = Objects.requireNonNull(date);
    }

    public UUID getUserId() { return userId; }
    public LocalDate getDate() { return date; }
}