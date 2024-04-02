package org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;

public record DetalleEnvioToSaveDto(
        String direccion,
        String transportadora,
        Integer numeroGuia,
        PedidoToShowDto pedido
) {
}
