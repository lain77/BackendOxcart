package com.oxcart.project.dto.response;

public record LoginResponseDTO(
        String token,
        Long id,
        String name,
        String email,
        Integer total_flights,
        Integer aircraft_count,
        Integer credits)
{
}
