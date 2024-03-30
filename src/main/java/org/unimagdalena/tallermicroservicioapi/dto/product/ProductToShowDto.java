package org.unimagdalena.tallermicroservicioapi.dto.product;

import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoDto;

import java.util.List;
import java.util.UUID;

public record ProductToShowDto(
        UUID id,
        String nombre,
        Float price,
        Integer stock
) {
}
