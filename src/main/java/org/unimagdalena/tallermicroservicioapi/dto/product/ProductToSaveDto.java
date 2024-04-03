package org.unimagdalena.tallermicroservicioapi.dto.product;


public record ProductToSaveDto(
        String nombre,
        Float price,
        Integer stock
) { }
