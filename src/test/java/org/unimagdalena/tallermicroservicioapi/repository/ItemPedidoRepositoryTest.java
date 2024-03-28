package org.unimagdalena.tallermicroservicioapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ItemPedidoRepositoryTest extends AbstractIntegrationDBTest {

    ProductRepository productRepository;
    ItemPedidoRepository itemPedidoRepository;
    PedidoRepository pedidoRepository;

    @Autowired
    public ItemPedidoRepositoryTest(ProductRepository productRepository, ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository){
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productRepository = productRepository;
    }

    Product productoGlobalCamisa;
    Product productoGlobalZapato;
    Pedido pedidoGlobal;
    ItemPedido ItemPedidoGlobalCamisa;
    ItemPedido ItemPedidoGlobalZapatos;

    @BeforeEach
    void setUp() {

        productRepository.deleteAll();
        itemPedidoRepository.deleteAll();
        pedidoRepository.deleteAll();

        productoGlobalCamisa = Product.builder()
                .nombre("Camisa XS Renzo")
                .price(78000.00f)
                .stock(100)
                .build();
        productoGlobalZapato = Product.builder()
                .nombre("Par Zapato talla 40 Nike")
                .price(350000.00f)
                .stock(250)
                .build();
        productRepository.save(productoGlobalCamisa);
        productRepository.save(productoGlobalZapato);

        pedidoGlobal = Pedido.builder().build();
        pedidoRepository.save(pedidoGlobal);

        ItemPedidoGlobalCamisa = ItemPedido.builder()
                .pedido(pedidoGlobal)
                .cantidad(5)
                .product(productoGlobalCamisa)
                .precioUnitario(productoGlobalCamisa.getPrice())
                .build();

        ItemPedidoGlobalZapatos = ItemPedido.builder()
                .pedido(pedidoGlobal)
                .cantidad(2)
                .product(productoGlobalZapato)
                .precioUnitario(productoGlobalZapato.getPrice())
                .build();

        itemPedidoRepository.save(ItemPedidoGlobalCamisa);
        itemPedidoRepository.save(ItemPedidoGlobalZapatos);
    }

    @Test
    @DisplayName("[findByPedidoId] dado un pedidoID buscar los items que lo conforma ")
    void test_findByPedidoId() {
        UUID id_pedido = pedidoGlobal.getId();
        List<ItemPedido> todosLosItemsDelPedido = itemPedidoRepository.findByPedidoId(id_pedido);
        assertThat(todosLosItemsDelPedido.size()).isEqualTo(2);
        assertThat(todosLosItemsDelPedido.get(0).getPedido().getId()).isEqualTo(id_pedido);
    }

    @Test
    void findByProductId() {
        UUID id_product = productoGlobalCamisa.getId();
        List<ItemPedido> items = itemPedidoRepository.findByProductId(id_product);
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getProduct().getId()).isEqualTo(id_product);
    }

    @Test
    void getTotalVentasByProductId() {
        UUID id_camisa = productoGlobalCamisa.getId();
        UUID id_zapato = productoGlobalZapato.getId();
        Float TOTAL_VENTA_CAMISA = 78000.00f * 5;
        Float TOTAL_VENTA_ZAPATO = 350000.00f * 2;
        Float totalGananciaCamisa = itemPedidoRepository.getTotalVentasByProductId(id_camisa);
        Float totalGananciaZapato = itemPedidoRepository.getTotalVentasByProductId(id_zapato);
        assertThat(totalGananciaCamisa).isEqualTo(TOTAL_VENTA_CAMISA);
        assertThat(totalGananciaZapato).isEqualTo(TOTAL_VENTA_ZAPATO);
    }

    @Test
    @DisplayName("[Save] debe guardar un item de pedido correctamente")
    void test_save() {
        ItemPedido itemGuardado = itemPedidoRepository.save(ItemPedidoGlobalCamisa);
        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(itemGuardado.getId());
        assertThat(itemPedidoConsultado).isPresent();
        assertThat(itemPedidoConsultado.get().getId()).isEqualTo(ItemPedidoGlobalCamisa.getId());
    }

    @Test
    @DisplayName("[read] debe poder obtener todos los items pedidos que hay")
    void test_findById() {
        List<ItemPedido> AllItemPedido = itemPedidoRepository.findAll();
        assertThat(AllItemPedido.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[Delete] debe eliminar un item de pedido correctamente")
    void test_delete() {
        itemPedidoRepository.deleteById(ItemPedidoGlobalCamisa.getId());
        Optional<ItemPedido> item = itemPedidoRepository.findById(ItemPedidoGlobalCamisa.getId());
        assertThat(item).isNotPresent();
    }

    @Test
    @DisplayName("[Update] debe actualizar un item de pedido correctamente")
    void test_update() {
        ItemPedidoGlobalCamisa.setCantidad(13);
        itemPedidoRepository.save(ItemPedidoGlobalCamisa);
        Optional<ItemPedido> itemActualizado = itemPedidoRepository.findById(ItemPedidoGlobalCamisa.getId());
        assertThat(itemActualizado).isPresent();
        assertThat(itemActualizado.get().getCantidad()).isEqualTo(13);
    }

}