package org.unimagdalena.tallermicroservicioapi.dto.product;

import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoDto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String nombre,
        Float price,
        Integer stock,
        List<ItemPedidoDto> itemsPedidos
) {

    public List<ItemPedidoDto> itemPedidos(){
        return Collections.unmodifiableList(itemsPedidos);
    }

}
