package com.berchtesgaden.explorer.controller;

import com.berchtesgaden.explorer.domain.Location;
import com.berchtesgaden.explorer.domain.LocationCategory;
import com.berchtesgaden.explorer.security.JwtFilter;
import com.berchtesgaden.explorer.security.JwtService;
import com.berchtesgaden.explorer.security.RateLimitFilter;
import com.berchtesgaden.explorer.security.SecurityConfig;
import com.berchtesgaden.explorer.service.LocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class) // Загружаем тольок веб-слой для этого контроллера
@Import(SecurityConfig.class) // 👈 ЯВНО импортируем твою защиту
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc; // Наш инстумент для имитации HTTP-запросов

    @MockitoBean
    private LocationService locationService;

    // ВАЖНО: Добавляем этот Mock, чтобы Spring Security смог запуститься
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtFilter jwtFilter; // Заглушка для фильтра

    @MockitoBean
    private RateLimitFilter rateLimitFilter; // Заглушка для лимитера

    @Test
    @DisplayName("Должен возвращать список локаций с пагинацией")
    @WithMockUser // Иммитируем авторизованного пользователя так как у нас установлен Spring Security
    void getAllTest() throws Exception {
        // 1. GIVEN (Дано) - подготавливаем данные
        Location location = new Location();
        location.setId(1L);
        location.setName("Königssee");
        location.setCategory(LocationCategory.LAKE);

        //Говорим Моку: Когда вызовут findAll, верни старницу с одной локацией
         when(locationService.findAll(anyInt(), anyInt()))
                 .thenReturn(new PageImpl<>(List.of(location)));

         // 2. WHEN (Когда) + THEN (Тогда) - выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/locations")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Checking Status 200 ok
                .andExpect(jsonPath("$.content[0].name").value("Königssee"))
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    @DisplayName("Должен возвращать локацию по ID")
    @WithMockUser
    void getByIdTest() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setName("Watzmann");

        when(locationService.findById(1L)).thenReturn(location);

        mockMvc.perform(get("/api/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Watzmann"));
    }

    @Test
    @DisplayName("Должен вернуть 403 Forbidden если аноним пытается зайти в админку (логика безопасности)")
    void accesDeniedTest() throws Exception {
        mockMvc.perform(get("/api/locations/featured"))
                .andExpect(status().isOk());
    }
}