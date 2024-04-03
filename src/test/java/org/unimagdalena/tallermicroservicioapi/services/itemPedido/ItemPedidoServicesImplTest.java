package org.unimagdalena.tallermicroservicioapi.services.itemPedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import org.unimagdalena.tallermicroservicioapi.mappers.ItemPedidoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ItemPedidoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.ProductRepository;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServicesImplTest {

    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @Mock
    private ItemPedidoRepository itemPedidoRepository;
    @Mock
    private ProductRepository productoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private ItemPedidoServicesImpl itemPedidoService;
    Cliente cliente;
    ClienteToShowDto clienteToShowDto;
    Pedido pedido,pedido2;
    PedidoToShowDto pedidoToShowDto, pedidoToShowDto2;
    Product product, product2;
    ProductToShowDto productToShowDto, productToShowDto2;
    ItemPedido itemPedido,itemPedido2;
    
    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();

        clienteToShowDto = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );

        pedido = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status(EstadoPedido.PENDIENTE)
                .build();

        pedidoToShowDto = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        pedido2 = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status(EstadoPedido.PENDIENTE)
                .build();

        pedidoToShowDto2 = new PedidoToShowDto(
                pedido2.getId(),
                pedido2.getFechaPedido(),
                pedido2.getStatus(),
                clienteToShowDto
        );

        product = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product test")
                .price((float) 1000)
                .stock(10)
                .build();

        product2 = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product2 test")
                .price((float) 1500)
                .stock(5)
                .build();

        productToShowDto = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        productToShowDto2 = new ProductToShowDto(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock()
        );

        itemPedido = ItemPedido.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .product(product)
                .cantidad(5)
                .precioUnitario((float) 1000)
                .build();

        itemPedido2 = ItemPedido.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .product(product2)
                .cantidad(4)
                .precioUnitario((float) 1500)
                .build();
    }


    @Test
    void getItemPedidoById() {
        ItemPedidoToShowDto itemPedidoDTO = new ItemPedidoToShowDto(
                itemPedido.getId(),
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario(),
                pedidoToShowDto,
                productToShowDto
        );

        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        given(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(any())).willReturn(itemPedidoDTO);

        ItemPedidoToShowDto itemPedidoFinded = itemPedidoService.getItemPedidoById(itemPedido.getId());

        assertThat(itemPedidoFinded).isNotNull();
        assertThat(itemPedidoFinded.id()).isEqualTo(itemPedido.getId());

    }

    @Test
    void getAllItemPedido() {
    }

    @Test
    void saveItemPedido() {
        ItemPedidoToShowDto itemPedidoDTO = new ItemPedidoToShowDto(
                itemPedido.getId(),
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario(),
                pedidoToShowDto,
                productToShowDto
        );

        given(productoRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(itemPedidoMapper.itemPedidoToSaveDtoToItemPedidoEntity(any())).willReturn(itemPedido);
        given(itemPedidoRepository.save(any())).willReturn(itemPedido);
        given(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(any())).willReturn(itemPedidoDTO);

        ItemPedidoToSaveDto itemPedidoToSave = new ItemPedidoToSaveDto(
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario(),
                pedidoToShowDto,
                productToShowDto
        );

        ItemPedidoToShowDto itemPedidoSaved = itemPedidoService.saveItemPedido(itemPedidoToSave);
        assertThat(itemPedidoSaved).isNotNull();
        assertThat(itemPedidoSaved.id()).isEqualTo(itemPedido.getId());
    }

    @Test
    void updateItemPedido() {
        ItemPedido itemPedidoUpdated = ItemPedido.builder()
                .id(itemPedido.getId())
                .pedido(itemPedido.getPedido())
                .product(itemPedido.getProduct())
                .cantidad(8)
                .precioUnitario(itemPedido.getPrecioUnitario())
                .build();

        ItemPedidoToShowDto itemPedidoUpdatedDTO = new ItemPedidoToShowDto(
                itemPedidoUpdated.getId(),
                itemPedidoUpdated.getCantidad(),
                itemPedidoUpdated.getPrecioUnitario(),
                pedidoToShowDto,
                productToShowDto
        );

        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        given(itemPedidoRepository.save(any())).willReturn(itemPedidoUpdated);
        given(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(any())).willReturn(itemPedidoUpdatedDTO);

        ItemPedidoToSaveDto itemPedidoToUpdate = new ItemPedidoToSaveDto(
                itemPedidoUpdated.getCantidad(),
                null,
                new PedidoToShowDto(pedido.getId(),null,null,null),
                new ProductToShowDto(product.getId(),null,null,null)
        );
        ItemPedidoToShowDto itemPedidoActualizado = itemPedidoService.updateItemPedido(itemPedido.getId(),itemPedidoToUpdate);
        assertThat(itemPedidoActualizado).isNotNull();
        assertThat(itemPedidoActualizado.id()).isEqualTo(itemPedido.getId());
        assertThat(itemPedidoActualizado.cantidad()).isEqualTo(itemPedidoUpdated.getCantidad());
    }

    @Test
    void findItemPedidoToShowDtoByPedidoId() {

    }

    @Test
    void findItemPedidoToShowDtoByProductId() {
    }

    @Test
    void deleteItemPedidoById() {
        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        willDoNothing().given(itemPedidoRepository).deleteById(any());
        itemPedidoService.deleteItemPedidoById(itemPedido.getId());
        verify(itemPedidoRepository,times(1)).deleteById(any());
    }
}