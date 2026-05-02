package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import com.berchtesgaden.explorer.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // return all active places
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    // Find by one ID
    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found:" + id));
    }

    // find one by Category
    public List<Location> findByCategory(LocationCategory category) {
        return locationRepository.findByCategory(category);
    }

    public List<Location> findFeatured() {
        return locationRepository.findByIsFeaturedTrue();
    }

    public Location create(Location location) {
        location.setIsActive(true);
        return locationRepository.save(location);
    }

    // Обновить место (только для ADMIN)
    public Location update(Long id, Location updated) {
        Location existing = findById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());
        existing.setCategory(updated.getCategory());
        existing.setDifficulty(updated.getDifficulty());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setBestSeason(updated.getBestSeason());
        existing.setTipText(updated.getTipText());
        existing.setIsFeatured(updated.getIsFeatured());
        return locationRepository.save(existing);
    }

    // Удалить место (только для ADMIN)
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }
}
