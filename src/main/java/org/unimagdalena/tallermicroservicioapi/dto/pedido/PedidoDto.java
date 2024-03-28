package org.unimagdalena.tallermicroservicioapi.dto.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record PedidoDto(
        UUID id,
        LocalDateTime fechaPedido,
        EstadoPedido status,
        PagoDto pago,
        DetalleEnvioDto detalleEnvio,
        ClienteDto cliente,
        List<ItemPedidoDto> itemsPedido

) {
    public List<ItemPedidoDto> itemsPedidos(){
        return Collections.unmodifiableList(itemsPedido);
    }
}
