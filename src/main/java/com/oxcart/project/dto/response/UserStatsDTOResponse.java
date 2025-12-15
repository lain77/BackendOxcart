package com.oxcart.project.dto.response; // Ajuste o pacote se necessário

// Use record (Java 17+) ou class com getters
public record UserStatsDTOResponse(
        Long id,               // ou String, dependendo do seu banco
        String name,
        String email,
        Integer total_flights, // Para mostrar na tela
        Integer aircraft_count,// Para mostrar na tela
        Integer credits        // Intel Points
) {}