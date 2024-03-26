package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    //buscar pedidos entre dos fecha
    Optional<Pedido> findByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Buscar pedidos por cliente y un estado
    Optional<Pedido> findByClienteIdAndStatus(UUID clienteId, EstadoPedido status);

    // Recuperar pedidos con sus artículos usando JOIN fetch para evitar el problema N+1, para un cliente específico
    @Query("SELECT p FROM Pedido p JOIN FETCH p.itemsPedido WHERE p.cliente.id = ?1")
    Optional<Pedido> findByClienteIdWithItemsFetch(Long clienteId);
}
