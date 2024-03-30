package org.unimagdalena.tallermicroservicioapi.services.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoServices {
    PedidoDto savePedido( PedidoToSaveDto pedido );
    PedidoDto updatePedidoById(UUID id, PedidoToSaveDto pedido );
    PedidoDto findPedidoById(UUID id);
    List<PedidoDto> findAllPedidos();
    void deletePedidoById(UUID id);
    List<PedidoDto> findPedidosByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PedidoDto> findPedidosByClienteIdAndStatus(UUID cliente_id, EstadoPedido status);
    List<PedidoDto> findPedidosByClienteIdWithItemsFetch(UUID cliente_id);

}
