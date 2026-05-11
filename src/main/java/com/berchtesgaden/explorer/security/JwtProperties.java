package com.berchtesgaden.explorer.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // Говорим Spring, что это конфигурационный бин
@ConfigurationProperties(prefix = "jwt") // Магия! Все настройки "jwt.*" попадут сюда автоматически
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private String expiration;
}

