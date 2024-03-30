package org.unimagdalena.tallermicroservicioapi.dto.cliente;

import java.util.UUID;

public record ClienteToShowDto(
        UUID id,
        String nombre,
        String email,
        String direccion
) { }
