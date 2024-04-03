package org.unimagdalena.tallermicroservicioapi.dto.cliente;

public record ClienteToSaveDto(
        String nombre,
        String email,
        String direccion
) { }