package org.unimagdalena.tallermicroservicioapi.dto.pago;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagoDto(
        UUID id,
        Integer totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        PedidoDto pedido
) { }
