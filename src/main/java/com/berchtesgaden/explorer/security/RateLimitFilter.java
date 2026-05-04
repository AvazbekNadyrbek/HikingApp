package com.berchtesgaden.explorer.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // Каждый IP имеет свой bucket
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        // 5 запросов в минуту на /api/auth/**
        Bandwidth limit = Bandwidth.classic(
                5,                           // 5 токенов
                Refill.greedy(5, Duration.ofMinutes(1)) // обновляется каждую минуту
        );
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Rate limit только для auth endpoints
        if (!request.getRequestURI().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Получаем IP пользователя
        String ip = request.getRemoteAddr();

        // Получаем или создаём bucket для этого IP
        Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

        // Пробуем взять токен
        if (bucket.tryConsume(1)) {
            // Токен есть — пропускаем запрос
            filterChain.doFilter(request, response);
        } else {
            // Токенов нет — слишком много запросов
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "status": 429,
                    "message": "Too many requests. Try again in 1 minute.",
                    "timestamp": "%s"
                }
                """.formatted(java.time.LocalDateTime.now()));
        }
    }
}