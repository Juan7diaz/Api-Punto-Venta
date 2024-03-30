package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;

public record PedidoToSaveDto(
        LocalDateTime fechaPedido,
        EstadoPedido status,
        ClienteDto cliente
) { }
