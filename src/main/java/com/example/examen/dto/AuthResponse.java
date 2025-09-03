package com.example.examen.dto;

/**
 * Un DTO que representa la respuesta exitosa de un login,
 * conteniendo el token JWT que el cliente debe usar para futuras peticiones.
 */
public record AuthResponse(String token, Integer usuId) {
}
