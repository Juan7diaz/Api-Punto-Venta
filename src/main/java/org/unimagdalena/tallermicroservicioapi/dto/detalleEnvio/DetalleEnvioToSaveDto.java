package org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;

public record DetalleEnvioToSaveDto(
        String direccion,
        String transportadora,
        Integer numeroGuia,
        PedidoDto pedido
) {
}
