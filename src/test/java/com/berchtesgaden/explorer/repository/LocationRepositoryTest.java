package com.berchtesgaden.explorer.repository;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {


    @Autowired
    private TestEntityManager entityManager; // 2. помощник для чистой записи в БД

    @Autowired
    private LocationRepository locationRepository; // 3.  Наш репозиторий котоорый тестируем

    @Test
    @DisplayName("Должен находить локации по части имени (регистронезависимо)")
    void shouldSearchByNameIgnoringCase() {
        //Given:  Подготавливаем почву
        Location watzmann = Location.builder()
                .name("Watzmann Mountain")
                .latitude(47.5)
                .longitude(12.3)
                .category(LocationCategory.HIKING)
                .isActive(true)
                .build();

        Location lake = Location.builder()
                .name("Konigsee Lake")
                .latitude(47.7)
                .longitude(12.9)
                .category(LocationCategory.LAKE)
                .isActive(true)
                .build();

        //Saving data via entity Manager
        entityManager.persist(watzmann);
        entityManager.persist(lake);
        entityManager.flush(); // принудительно отправляем данные в базу

        //When Выполняем наш кастомный метод поиска

        List<Location> result = locationRepository.search("watz");

        // Then  Проверяем что поиска работает
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Watzmann Mountain");
    }
}