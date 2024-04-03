package org.unimagdalena.tallermicroservicioapi.dto.itemPedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;

import java.util.UUID;

public record ItemPedidoToShowDto(
        UUID id,
        Integer cantidad,
        Float precioUnitario,
        PedidoToShowDto pedido,
        ProductToShowDto product
) { }
