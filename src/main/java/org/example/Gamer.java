package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.checker.nullness.qual.NonNull;

@Data
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Gamer extends Person {
    // https://checkerframework.org/manual/#nullness-checks
    @NonNull private final String favoriteGame;
}
