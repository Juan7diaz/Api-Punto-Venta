package entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class DetalleEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column
    private String direccion;

    @Column
    private String trasnportadora;

    @Column
    private Integer numeroGuia;

}
