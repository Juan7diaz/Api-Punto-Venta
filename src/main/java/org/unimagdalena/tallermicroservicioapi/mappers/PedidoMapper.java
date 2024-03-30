package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido pedidoDtoToPedidoEntity(PedidoDto pedidoDto);

    PedidoDto pedidoEntityToPedidoDto(Pedido pedido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pago", expression = "java(new Pago())")
    Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto);

    PedidoToSaveDto pedidoEntityToPedidoToSaveDto(Pedido pedido);

}
