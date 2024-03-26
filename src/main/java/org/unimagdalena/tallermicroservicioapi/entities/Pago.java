package org.unimagdalena.tallermicroservicioapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Integer totalPago;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;

    @Column
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

}
