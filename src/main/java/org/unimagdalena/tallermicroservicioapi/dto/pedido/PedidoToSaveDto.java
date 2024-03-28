package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoToSaveDto(
        LocalDateTime fechaPedido,
        EstadoPedido status,
        DetalleEnvioDto detalleEnvio,
        ClienteDto cliente,
        List<ItemPedidoDto> itemsPedido
) {
}
