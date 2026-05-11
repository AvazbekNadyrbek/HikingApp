package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.exception.LocationNotFoundException;
import com.berchtesgaden.explorer.repository.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    @DisplayName("Должен успешно найти локацию по ID")
    void findById_Success() {
        // Given
        Location mockLocation = Location.builder().id(1L).name("Watzmann").build();
        when(locationRepository.findById(1L)).thenReturn(Optional.of(mockLocation));

        // When
        Location result = locationService.findById(1L);

        // Then
        assertThat(result.getName()).isEqualTo(mockLocation.getName());
    }

    @Test
    @DisplayName("Должен выкинуть исключение, если локация не найдена")
    void findById_NotFound() {
        // Given
        when(locationRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        assertThatThrownBy(() -> locationService.findById(99L))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location not found: 99");
    }
}
