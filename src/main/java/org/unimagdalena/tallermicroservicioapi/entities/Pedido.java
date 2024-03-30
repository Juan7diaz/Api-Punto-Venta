package org.unimagdalena.tallermicroservicioapi.entities;

import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    private LocalDateTime fechaPedido;

    @Column
    @Enumerated(EnumType.STRING)
    private EstadoPedido status;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pago pago;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private DetalleEnvio detalleEnvio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPedido> itemsPedido;

}
