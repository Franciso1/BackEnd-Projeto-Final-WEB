package com.fatec.contatos_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a regra para todos os endpoints que começam com /api/
            .allowedOrigins("https://main.d35dmr9tr2ye12.amplifyapp.com") // A URL EXATA do seu frontend no Amplify
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Permite todos os métodos que usamos
            .allowedHeaders("*") // Permite todos os cabeçalhos
            .allowCredentials(true);
    }
}