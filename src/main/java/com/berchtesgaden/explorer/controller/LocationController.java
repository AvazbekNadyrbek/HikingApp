package com.berchtesgaden.explorer.controller;


import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import com.berchtesgaden.explorer.repository.LocationRepository;
import com.berchtesgaden.explorer.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    // Публичный — все туристы видят
    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Location>> getByCategory(@PathVariable LocationCategory category) {
        return ResponseEntity.ok(locationService.findByCategory(category));
    }

    // Публичный — featured места для главного экрана
    @GetMapping("/featured")
    public ResponseEntity<List<Location>> getFeatured() {
        return ResponseEntity.ok(locationService.findFeatured());
    }

    // Только ADMIN — добавить место
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> create(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.create(location));
    }

    // Только ADMIN — обновить место
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> update(
            @PathVariable Long id,
            @RequestBody Location location) {
        return ResponseEntity.ok(locationService.update(id, location));
    }

    // Только ADMIN — удалить место
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
