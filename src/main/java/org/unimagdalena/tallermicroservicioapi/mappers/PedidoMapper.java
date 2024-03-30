package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido pedidoDtoToPedidoEntity(PedidoDto pedidoDto);

    @Mapping(target = "cliente", ignore = true)
    PedidoDto pedidoEntityToPedidoDto(Pedido pedido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "detalleEnvio",  ignore = true)
    @Mapping(target = "itemsPedido", expression = "java(new ArrayList<ItemPedido>())")
    @Mapping(target = "cliente.pedidos", expression = "java(new ArrayList<Pedido>())" )
    Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto);

    PedidoToSaveDto pedidoEntityToPedidoToSaveDto(Pedido pedido);

    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "detalleEnvio",  ignore = true)
    @Mapping(target = "itemsPedido",  ignore = true)
    @Mapping(target = "cliente.pedidos",  ignore = true)
    Pedido pedidoToShowDtoToPedidoEntity(PedidoToShowDto pedidoToShowDto);

    PedidoToShowDto pedidoEntityToPedidoToShow(Pedido pedido);

}
