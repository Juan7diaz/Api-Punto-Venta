package org.unimagdalena.tallermicroservicioapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class PedidoRepositoryTest extends AbstractIntegrationDBTest {

    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ItemPedidoRepository itemPedidoRepository){
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }


    Pedido pedidoGlobal1;
    Pedido pedidoGlobal2;
    Pedido pedidoGlobal3;

    Cliente clienteGlobal1;

    ItemPedido itemPedidoGlobal1;
    ItemPedido itemPedidoGlobal2;
    ItemPedido itemPedidoGlobal3;


    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        itemPedidoRepository.deleteAll();

        itemPedidoGlobal1 = ItemPedido.builder().precioUnitario(12f).build();
        itemPedidoGlobal2 = ItemPedido.builder().precioUnitario(12f).build();
        itemPedidoGlobal3 = ItemPedido.builder().precioUnitario(12f).build();
        itemPedidoRepository.save(itemPedidoGlobal1);
        itemPedidoRepository.save(itemPedidoGlobal2);
        itemPedidoRepository.save(itemPedidoGlobal3);

        clienteGlobal1 = Cliente.builder().build();
        clienteRepository.save(clienteGlobal1);

        pedidoGlobal1 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2002, 4, 12, 2, 3, 4))
                .status(EstadoPedido.ENTREGADO)
                .cliente(clienteGlobal1)
                .itemsPedido(List.of(itemPedidoGlobal1))
                .build();

        pedidoGlobal2 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2012, 7, 23, 16, 3, 42))
                .status(EstadoPedido.PENDIENTE)
                .cliente(clienteGlobal1)
                .itemsPedido(List.of(itemPedidoGlobal2))
                .build();

        pedidoGlobal3 = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2020, 8, 16, 13, 13, 32))
                .status(EstadoPedido.ENTREGADO)
                .cliente(clienteGlobal1)
                .itemsPedido(List.of(itemPedidoGlobal3))
                .build();


        pedidoRepository.save(pedidoGlobal1);
        pedidoRepository.save(pedidoGlobal2);
        pedidoRepository.save(pedidoGlobal3);
    }

    @Test
    @DisplayName("[findByFechaPedidoBetween] dado un rango de fecha se debe poder obtener los pedidos que se hayan realizado en esa fecha")
    void test_findByFechaPedidoBetween() {
        LocalDateTime fechaInicial = LocalDateTime.of(2010, 1, 1, 1,1,1);
        LocalDateTime fechaFinal = LocalDateTime.of(2024, 1, 1, 1,1,1);
        List<Pedido> pedidosMatch = pedidoRepository.findByFechaPedidoBetween(fechaInicial, fechaFinal);

        assertThat(pedidosMatch.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[findByClienteIdAndStatus] dado un id de cliente y un status del pedido se debe obtener todos los pedidos que hagan match")
    void test_findByClienteIdAndStatus() {
        UUID cliente_id = clienteGlobal1.getId();
        EstadoPedido estado_entregado = EstadoPedido.ENTREGADO;
        EstadoPedido estado_pendiente = EstadoPedido.PENDIENTE;
        EstadoPedido estado_enviado = EstadoPedido.ENVIADO;

        List<Pedido> listPedidosEntregado = pedidoRepository.findByClienteIdAndStatus(cliente_id, estado_entregado);
        List<Pedido> listPedidosPendiente = pedidoRepository.findByClienteIdAndStatus(cliente_id, estado_pendiente);
        List<Pedido> listPedidosEnviado = pedidoRepository.findByClienteIdAndStatus(cliente_id, estado_enviado);

        assertThat(listPedidosEntregado.size()).isEqualTo(2);
        assertThat(listPedidosPendiente.size()).isEqualTo(1);
        assertThat(listPedidosEnviado.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("[findByClienteIdWithItemsFetch]")
    void test_findByClienteIdWithItemsFetch() {
        //TODO: Terminar el test
    }

    @Test
    @DisplayName("[read] debe poder consultar todos los pedidos guardados")
    void test_read(){
        List<Pedido> todosLosPedidos = pedidoRepository.findAll();
        assertThat(todosLosPedidos.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("[update] se debe poder actualizar un pedido de la base de datos")
    void test_update(){
        UUID pedidoGlobal1ID = pedidoGlobal1.getId();
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(pedidoGlobal1ID);

        assertThat(pedidoBuscado).isPresent();
        assertThat(pedidoBuscado.get().getId()).isEqualTo(pedidoGlobal1ID);
        assertThat(pedidoBuscado.get().getFechaPedido()).isEqualTo(LocalDateTime.of(2002, 4, 12, 2, 3, 4));

        Pedido pedidoActualizado = pedidoBuscado.get();
        pedidoActualizado.setFechaPedido(LocalDateTime.of(2020, 4, 12, 2, 3, 4));

        assertThat(pedidoActualizado.getFechaPedido()).isEqualTo(LocalDateTime.of(2020, 4, 12, 2, 3, 4));
    }

    @Test
    @DisplayName("[save] dado un nuevo pedido se debe poder conservar en la base de datos")
    void test_save(){
        Pedido newPedido = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2025, 7, 23, 16, 3, 42))
                .status(EstadoPedido.ENVIADO)
                .cliente(clienteGlobal1)
                .itemsPedido(List.of(itemPedidoGlobal2))
                .build();

        Pedido pedidoGuardado = pedidoRepository.save(newPedido);

        assertThat(pedidoGuardado.getId()).isNotNull();
        assertThat(pedidoGuardado.getStatus()).isEqualTo(EstadoPedido.ENVIADO);
    }

    @Test
    @DisplayName("[delete] se debe poder borrar los registros de un producto en la base de datos")
    void test_delete(){
        pedidoRepository.deleteById(pedidoGlobal1.getId());
        Optional<Pedido> pedidoBorrado = pedidoRepository.findById(pedidoGlobal1.getId());
        assertThat(pedidoBorrado).isNotPresent();
    }

}