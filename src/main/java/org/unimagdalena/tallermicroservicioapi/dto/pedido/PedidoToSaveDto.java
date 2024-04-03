package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;

public record PedidoToSaveDto(
        LocalDateTime fechaPedido,
        EstadoPedido status,
        ClienteToShowDto cliente
) { }
