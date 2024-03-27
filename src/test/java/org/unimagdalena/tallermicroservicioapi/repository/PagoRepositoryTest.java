package org.unimagdalena.tallermicroservicioapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class PagoRepositoryTest extends AbstractIntegrationDBTest {

    PagoRepository pagoRepository;
    PedidoRepository pedidoRepository;

    @Autowired
    public PagoRepositoryTest( PagoRepository pagoRepository, PedidoRepository pedidoRepository ){
        this.pagoRepository = pagoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    Pago pagoCliente1;
    Pago pagoCliente2;
    Pedido pedidoGenerico1;
    Pedido pedidoGenerico2;
    @BeforeEach
    void setUp() {

        pedidoRepository.deleteAll();
        pagoRepository.deleteAll();

        pedidoGenerico1 = Pedido.builder().build();
        pedidoGenerico2 = Pedido.builder().build();
        pedidoRepository.save(pedidoGenerico1);
        pedidoRepository.save(pedidoGenerico2);

        pagoCliente1 = Pago.builder()
                .fechaPago(LocalDateTime.of(2002, 12, 7, 11, 15))
                .metodoPago(MetodoPago.fromString("EFECTIVO"))
                .totalPago(1500)
                .pedido(pedidoGenerico1)
                .build();

        pagoCliente2 = Pago.builder()
                .fechaPago(LocalDateTime.of(2022, 3, 12, 15, 15))
                .metodoPago(MetodoPago.fromString("NEQUI"))
                .totalPago(67000)
                .pedido(pedidoGenerico2)
                .build();

        pagoRepository.save(pagoCliente1);
        pagoRepository.save(pagoCliente2);

    }

    @Test
    @DisplayName("[test_findByFechaPagoBetween] buscar los pagos que se encuentre dentro de un rango de fecha")
    void test_findByFechaPagoBetween() {

        LocalDateTime fechaInicial = LocalDateTime.of(2010, 10, 10, 10, 10);
        LocalDateTime fechaFinal = LocalDateTime.of(2024, 3, 27, 10, 10);

        List<Pago> pagosMatchConRangofecha = pagoRepository.findByFechaPagoBetween(fechaInicial, fechaFinal);

        assertThat(pagosMatchConRangofecha.size()).isEqualTo(1);
        assertThat(pagosMatchConRangofecha.get(0).getId()).isEqualTo(pagoCliente2.getId());

    }

    @Test
    @DisplayName("[test_findByPedidoIdAndMetodoPago] dado el id de un pedido y un metodo de pago debe retornar los pagos que hagan match")
    void test_findByPedidoIdAndMetodoPago() {

        MetodoPago metodopago = MetodoPago.fromString("EFECTIVO");
        UUID id_pedido1 = pedidoGenerico1.getId();

        List<Pago> pagosMatch = pagoRepository.findByPedidoIdAndMetodoPago(id_pedido1, metodopago);

        assertThat(pagosMatch.size()).isEqualTo(1);
        assertThat(pagosMatch.get(0).getPedido().getId()).isEqualTo(id_pedido1);
        assertThat(pagosMatch.get(0).getMetodoPago()).isEqualTo(metodopago);

    }

    @Test
    @DisplayName("[read] se debe poder obtener todos los pagos registrados")
    void test_read(){
        List<Pago> todosLosPagos = pagoRepository.findAll();
        assertThat(todosLosPagos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[update] se debe poder cambiar valores de un pago")
    void test_update(){

        UUID pago_id = pagoCliente1.getId();
        MetodoPago metodoPagoEfectivo = MetodoPago.fromString("EFECTIVO");
        MetodoPago metodoPagoPse = MetodoPago.fromString("PSE");

        Optional<Pago> pagoEncontrado = pagoRepository.findById(pago_id);

        assertThat(pagoEncontrado).isPresent();
        assertThat(pagoEncontrado.get().getId()).isEqualTo(pago_id);
        assertThat(pagoEncontrado.get().getMetodoPago()).isEqualTo(metodoPagoEfectivo);

        Pago pagoActualizado = pagoEncontrado.get();
        pagoActualizado.setMetodoPago(metodoPagoPse);

        pagoRepository.save(pagoActualizado);

        assertThat(pagoActualizado.getId()).isEqualTo(pago_id);
        assertThat(pagoActualizado.getMetodoPago()).isEqualTo(metodoPagoPse);

    }

    @Test
    @DisplayName("[delete] dado un pago existente cuando se elimine se debe de eliminar de la base de datos")
    void test_delete(){

        UUID pago_id = pagoCliente1.getId();
        Optional<Pago> pagoEncontrado = pagoRepository.findById(pago_id);

        assertThat(pagoEncontrado).isPresent();
        assertThat(pagoEncontrado.get().getId()).isEqualTo(pago_id);

        pagoRepository.deleteById(pagoEncontrado.get().getId());

        Optional<Pago> pagoEncontrado2 = pagoRepository.findById(pago_id);
        assertThat(pagoEncontrado2).isEmpty();

    }

    @Test
    void test_save(){

        Pago newPago = Pago.builder()
                .fechaPago(LocalDateTime.of(1993, 7, 20, 16, 34))
                .metodoPago(MetodoPago.fromString("PAYPAL"))
                .totalPago(23451)
                .build();

        Pago pagoGuardado = pagoRepository.save(newPago);
        assertThat(pagoGuardado.getId()).isNotNull();

    }


}