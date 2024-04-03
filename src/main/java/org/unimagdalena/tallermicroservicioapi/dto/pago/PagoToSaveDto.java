package org.unimagdalena.tallermicroservicioapi.dto.pago;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;

public record PagoToSaveDto(
        Integer totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        PedidoToShowDto pedido
) { }
