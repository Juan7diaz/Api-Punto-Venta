package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagoRepository  extends JpaRepository<Pago, UUID> {

    //Recuperar pagos dentro de un rango de fecha
    List<Pago> findByFechaPagoBetween(LocalDateTime startDate, LocalDateTime endDate);

    //Recuperar pagos por un identificador de una orden y método de pago
    List<Pago> findByPedidoIdAndMetodoPago(UUID PedidoId, MetodoPago metodoPago);

}
