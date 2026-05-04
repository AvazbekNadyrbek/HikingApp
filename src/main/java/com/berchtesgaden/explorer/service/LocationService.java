package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import com.berchtesgaden.explorer.exception.LocationNotFoundException;
import com.berchtesgaden.explorer.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // Все активные места с пагинацией
    public Page<Location> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,               // номер страницы (0, 1, 2...)
                size,               // количество на странице
                Sort.by("name").ascending() // сортировка по имени
        );
        return locationRepository.findByIsActiveTrue(pageable);
    }

    // Поиск
    public List<Location> search(String query) {
        return locationRepository.search(query);
    }

    // Find by one ID
    public Location findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
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
