package entities;

import jakarta.persistence.*;
import lombok.*;
import utils.MetodoPago;

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

}
