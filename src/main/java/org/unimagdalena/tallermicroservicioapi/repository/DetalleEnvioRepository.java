package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.util.Optional;
import java.util.UUID;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, UUID> {

    //Buscar los detalles del envío por pedido Id
    Optional<DetalleEnvio> findByPedidoId(UUID pedidoId);

    //Buscar los detalles de envío para un transportadora
    Optional<DetalleEnvio> findByTransportadora(String transportadora);

    //Buscar los detalles de envio por estado
    Optional<DetalleEnvio> findByPedido_Status(EstadoPedido status);

}
