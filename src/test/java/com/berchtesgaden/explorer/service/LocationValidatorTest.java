package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationValidatorTest {

    private final LocationValidator validator = new LocationValidator();

    @Test
    @DisplayName("Должен вернуть true, если все поля заполнены верно")
    void shouldBeValid() {
        // GIVEN
        Location location = Location.builder()
                .name("Königssee")
                .latitude(47.55)
                .longitude(12.98)
                .description("Очень красивое озеро в Альпах")
                .build();

        // WHEN
        boolean result = validator.isReadyForPublication(location);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Должен вернуть false, если описание слишком короткое")
    void shouldBeInvalidWhenDescriptionIsShort() {
        // GIVEN
        Location location = Location.builder()
                .name("Test")
                .latitude(10.0)
                .longitude(10.0)
                .description("Коротко")
                .build();

        // WHEN
        boolean result = validator.isReadyForPublication(location);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Checking for null name")
    void isNameEquealNull() {

        Location location = Location.builder()
                .name(null)
                .latitude(47.0)
                .longitude(12.0)
                .description("Konnigsee")
                .build();

        boolean result = validator.isReadyForPublication(location);

        assertThat(result).isFalse();

    }
}
