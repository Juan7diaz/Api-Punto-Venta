package org.unimagdalena.tallermicroservicioapi.dto.cliente;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record ClienteDto(
        UUID id,
        String nombre,
        String email,
        String direccion,
        List<PedidoDto> pedidos

) {
    public List<PedidoDto> pedidos(){
        return Collections.unmodifiableList(pedidos);
    }
}
