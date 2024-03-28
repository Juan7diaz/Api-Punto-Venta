package org.unimagdalena.tallermicroservicioapi.dto.itemPedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;

public record ItemPedidoToSaveDto(
        Integer cantidad,
        Float precioUnitario,
        PedidoDto pedido,
        ProductDto product
) {
}
