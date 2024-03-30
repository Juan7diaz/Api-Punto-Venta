package org.unimagdalena.tallermicroservicioapi.services.pedido;

import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoServices {
    PedidoToShowDto savePedido(PedidoToSaveDto pedido );
    PedidoToShowDto updatePedidoById(UUID id, PedidoToSaveDto pedido );
    PedidoToShowDto findPedidoById(UUID id);
    List<PedidoToShowDto> findAllPedidos();
    void deletePedidoById(UUID id);
    List<PedidoToShowDto> findPedidosByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PedidoToShowDto> findPedidosByClienteIdAndStatus(UUID cliente_id, EstadoPedido status);
    List<PedidoToShowDto> findPedidosByClienteIdWithItemsFetch(UUID cliente_id);

}
