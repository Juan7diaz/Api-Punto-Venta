package org.unimagdalena.tallermicroservicioapi.entities;

import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;

    @Column
    @Enumerated(EnumType.STRING)
    private EstadoPedido status;

    @OneToOne(mappedBy = "pedido")
    private Pago pago;

    @OneToOne(mappedBy = "pedido")
    private DetalleEnvio detalleEnvio;

}
