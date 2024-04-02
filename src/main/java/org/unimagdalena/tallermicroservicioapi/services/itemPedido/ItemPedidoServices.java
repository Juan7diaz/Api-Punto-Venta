package org.unimagdalena.tallermicroservicioapi.services.itemPedido;

import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoServices {

    ItemPedidoToShowDto getItemPedidoById(UUID id);
    List<ItemPedidoToShowDto> getAllItemPedido();
    ItemPedidoToShowDto saveItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto);
    ItemPedidoToShowDto updateItemPedido(UUID itemPedidoId, ItemPedidoToSaveDto itemPedidoToSaveDto);
    List<ItemPedidoToShowDto> findItemPedidoToShowDtoByPedidoId(UUID pedidoId);
    List<ItemPedidoToShowDto> findItemPedidoToShowDtoByProductId(UUID productId);
    void deleteItemPedidoById(UUID id);

}
