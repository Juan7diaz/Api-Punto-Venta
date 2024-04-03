package org.unimagdalena.tallermicroservicioapi.dto.itemPedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;

public record ItemPedidoToSaveDto(
        Integer cantidad,
        Float precioUnitario,
        PedidoToShowDto pedido,
        ProductToShowDto product
) {
}
