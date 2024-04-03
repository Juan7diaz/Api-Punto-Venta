package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoToShowDto(
        UUID id,
        LocalDateTime fechaPedido,
        EstadoPedido status,
        ClienteToShowDto cliente
) { }
