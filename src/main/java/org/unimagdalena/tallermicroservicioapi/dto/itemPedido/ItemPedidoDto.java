package org.unimagdalena.tallermicroservicioapi.dto.itemPedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;

import java.util.UUID;

public record ItemPedidoDto(
        UUID id,
        Integer cantidad,
        Float precioUnitario,
        PedidoDto pedido,
        ProductDto product
) {
}
