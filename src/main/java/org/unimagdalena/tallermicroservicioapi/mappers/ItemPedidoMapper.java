package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;

@Mapper
public interface ItemPedidoMapper {

    ItemPedido itemPedidoDtoToItemPedidoEntity(ItemPedidoDto itemPedidoDto);

    ItemPedidoDto itemPedidoEntityToItemPedidoDto(ItemPedido itemPedido);

    @Mapping(target = "id", ignore = true)
    ItemPedido itemPedidoToSaveDtoToItemPedidoEntity(ItemPedidoToSaveDto itemPedidoToSaveDto);

    ItemPedidoToSaveDto itemPedidoEntityToItemPedidoToSaveDto(ItemPedido itemPedido);

}
