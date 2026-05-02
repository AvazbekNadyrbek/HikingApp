package com.berchtesgaden.explorer.repository;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByCategory(LocationCategory category);

    List<Location> findByIsActiveTrue();

    List<Location> findByIsFeaturedTrue();

}
