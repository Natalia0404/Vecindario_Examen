package com.example.examen.dto;

/**
 * Un DTO (Data Transfer Object) que representa la información
 * necesaria para que un usuario inicie sesión.
 * Usamos un 'record' de Java para una clase de datos inmutable y concisa.
 */
public record LoginRequest(String correo, String password) {
}
