package com.berchtesgaden.explorer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Integer altitudeMeters;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationCategory category;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    private Double distanceKm;

    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    private Season bestSeason;

    @Column(nullable = false)
    private Boolean isActive = true;

    private Boolean isFeatured = false;

    @Column(length = 300)
    private String tipText;

}
