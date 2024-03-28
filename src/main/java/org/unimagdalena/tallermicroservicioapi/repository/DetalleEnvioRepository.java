package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, UUID> {

    //Buscar los detalles del envío por pedido Id
    Optional<DetalleEnvio> findByPedidoId(UUID pedidoId);

    //Buscar los detalles de envío para una transportadora
    List<DetalleEnvio> findByTransportadora(String transportadora);

    //Buscar los detalles de envio por estado
    List<DetalleEnvio> findByPedido_Status(EstadoPedido status);

}
