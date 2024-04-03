package org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;

import java.util.UUID;

public record DetalleEnvioToShowDto (
    UUID id,
    String direccion,
    String transportadora,
    Integer numeroGuia,
    PedidoToShowDto pedido
) { }
