package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;

public record PedidoToSaveDto(
        LocalDateTime fechaPedido,
        EstadoPedido status,
        ClienteDto cliente
) { }
