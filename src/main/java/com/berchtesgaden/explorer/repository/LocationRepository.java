package com.berchtesgaden.explorer.repository;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Пагинация — возвращает Page вместо List
    Page<Location> findByIsActiveTrue(Pageable pageable);

    List<Location> findByCategory(LocationCategory category);

    List<Location> findByIsFeaturedTrue();

    // Поиск по названию и описанию
    @Query("SELECT l FROM Location l WHERE " +
            "LOWER(l.name) LIKE LOWER(CONCAT('%фв', :query, '%')) OR " +
            "LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Location> search(@Param("query") String query);
}
