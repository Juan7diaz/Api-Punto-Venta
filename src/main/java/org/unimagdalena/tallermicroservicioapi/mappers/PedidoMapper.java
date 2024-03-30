package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido pedidoDtoToPedidoEntity(PedidoDto pedidoDto);

    PedidoDto pedidoEntityToPedidoDto(Pedido pedido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pago",  expression = "java(new Pago())")
    @Mapping(target = "detalleEnvio",  expression = "java(new DetalleEnvio())")
    @Mapping(target = "itemsPedido", expression = "java(new ArrayList<>())")
    Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto);

    PedidoToSaveDto pedidoEntityToPedidoToSaveDto(Pedido pedido);

}
