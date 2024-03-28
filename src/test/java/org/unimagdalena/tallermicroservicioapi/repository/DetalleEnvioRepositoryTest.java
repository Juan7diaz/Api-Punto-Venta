package org.unimagdalena.tallermicroservicioapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class DetalleEnvioRepositoryTest extends AbstractIntegrationDBTest {


    DetalleEnvioRepository detalleEnvioRepository;
    PedidoRepository pedidoRepository;

    @Autowired
    public DetalleEnvioRepositoryTest(DetalleEnvioRepository detalleEnvioRepository, PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
        this.detalleEnvioRepository = detalleEnvioRepository;
    }


    Pedido pedidoGlobal1;
    Pedido pedidoGlobal2;

    DetalleEnvio detalleEnvioGlobal1;
    DetalleEnvio detalleEnvioGlobal2;


    @BeforeEach
    void setUp() {

        pedidoGlobal1 = Pedido.builder()
                .status(EstadoPedido.PENDIENTE)
                .build();
        pedidoGlobal2 = Pedido.builder()
                .status(EstadoPedido.PENDIENTE)
                .build();

        pedidoRepository.save(pedidoGlobal1);
        pedidoRepository.save(pedidoGlobal2);

        detalleEnvioGlobal1 = DetalleEnvio.builder()
                .direccion("santa marta calle 43 carrera 14")
                .transportadora("servientrega")
                .numeroGuia(314413)
                .pedido(pedidoGlobal1)
                .build();

        detalleEnvioGlobal2 = DetalleEnvio.builder()
                .direccion("Cienaga calle 4 carrera 4")
                .transportadora("servientrega")
                .numeroGuia(319392)
                .pedido(pedidoGlobal2)
                .build();

        detalleEnvioRepository.save(detalleEnvioGlobal1);
        detalleEnvioRepository.save(detalleEnvioGlobal2);

    }

    @Test
    @DisplayName("[findByPedidoId] dado el id de un pedido, me tiene que devolver los detalles de envio de dicho pedido")
    void test_findByPedidoId() {
        UUID idPedido = pedidoGlobal1.getId();
        Optional<DetalleEnvio> detalleEnvioEncontrado = detalleEnvioRepository.findByPedidoId(idPedido);

        assertThat(detalleEnvioEncontrado).isPresent();
        assertThat(detalleEnvioEncontrado.get().getPedido().getId()).isEqualTo(idPedido);
        assertThat(detalleEnvioEncontrado.get().getId()).isEqualTo(detalleEnvioGlobal1.getId());
    }

    @Test
    void findByTransportadora() {
        List<DetalleEnvio> datallesMatchTransportadora = detalleEnvioRepository.findByTransportadora("servientrega");
        assertThat(datallesMatchTransportadora.size()).isEqualTo(2);
        assertThat(datallesMatchTransportadora.get(1).getTransportadora()).isEqualTo("servientrega");
    }

    @Test
    void findByPedido_Status() {
        List<DetalleEnvio> detallesEnvioEnPendiente = detalleEnvioRepository.findByPedido_Status(EstadoPedido.PENDIENTE);
        assertThat(detallesEnvioEnPendiente.size()).isEqualTo(2);
        assertThat(detallesEnvioEnPendiente.get(0).getPedido().getStatus()).isEqualTo(EstadoPedido.PENDIENTE);

    }

    @Test
    @DisplayName("[Save] debe guardar un detalle de envío correctamente")
    void test_save() {

        DetalleEnvio nuevoDetalle = DetalleEnvio.builder()
                .direccion("Barranquilla en la casa de shakira")
                .numeroGuia(21313)
                .transportadora("EntregaYA")
                .build();

        DetalleEnvio DetalleEnvioGuardado = detalleEnvioRepository.save(nuevoDetalle);

        Optional<DetalleEnvio> detalleEnvioRecuperado = detalleEnvioRepository.findById(DetalleEnvioGuardado.getId());

        assertThat(detalleEnvioRecuperado).isPresent();
        assertThat(detalleEnvioRecuperado.get().getId()).isEqualTo(DetalleEnvioGuardado.getId());
    }

    @Test
    @DisplayName("[Read] debe de poder devolver todos los detalles de envios")
    void test_read() {
        List<DetalleEnvio> todosLosDetalles = detalleEnvioRepository.findAll();
        assertThat(todosLosDetalles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[Delete] debe eliminar un detalle de envío correctamente")
    void test_delete() {
        detalleEnvioRepository.delete(detalleEnvioGlobal1);
        Optional<DetalleEnvio> detalleEliminado = detalleEnvioRepository.findById(detalleEnvioGlobal1.getId());
        assertThat(detalleEliminado).isNotPresent();
    }

    @Test
    @DisplayName("[Update] debe actualizar un detalle de envío correctamente")
    void test_update() {

        DetalleEnvio nuevoDetalle = DetalleEnvio.builder()
                .direccion("Barranquilla en la casa de shakira")
                .numeroGuia(21313)
                .transportadora("EntregaYA")
                .build();

        DetalleEnvio DetalleEnvioGuardado = detalleEnvioRepository.save(nuevoDetalle);

        DetalleEnvioGuardado.setDireccion("Barranquilla en la estatua de shakira");
        detalleEnvioRepository.save(DetalleEnvioGuardado);

        Optional<DetalleEnvio> DetalleEnvioActualizado = detalleEnvioRepository.findById(DetalleEnvioGuardado.getId());

        assertThat(DetalleEnvioActualizado).isPresent();
        assertThat(DetalleEnvioActualizado.get().getDireccion()).isEqualTo("Barranquilla en la estatua de shakira");
    }


}