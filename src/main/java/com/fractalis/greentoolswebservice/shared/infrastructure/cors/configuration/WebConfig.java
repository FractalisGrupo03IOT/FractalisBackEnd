package com.fractalis.greentoolswebservice.shared.infrastructure.cors.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite credenciales si es necesario
        config.addAllowedOriginPattern("*"); // Permitir todos los orígenes, incluyendo 'localhost'
        config.addAllowedHeader("*"); // Permitir todas las cabeceras
        config.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, PUT, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
