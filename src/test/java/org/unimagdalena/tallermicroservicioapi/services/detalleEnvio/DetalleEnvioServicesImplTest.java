package org.unimagdalena.tallermicroservicioapi.services.detalleEnvio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.mappers.DetalleEnvioMapper;
import org.unimagdalena.tallermicroservicioapi.mappers.PagoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.DetalleEnvioRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PagoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.services.pago.PagoServicesImpl;
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
class DetalleEnvioServicesImplTest {

    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;
    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private DetalleEnvioServicesImpl detalleEnvioService;
    Cliente cliente;
    ClienteToShowDto clienteToShowDto;
    Pedido pedido;
    Pedido pedido2;
    PedidoToShowDto pedidoToShowDto;
    PedidoToShowDto pedidoToShowDto2;
    DetalleEnvio detalleEnvio;
    DetalleEnvio detalleEnvio2;

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

        detalleEnvio = DetalleEnvio.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .direccion("Cra Test #Test-01")
                .transportadora("COORDINADORA")
                .numeroGuia(593324)
                .build();

        detalleEnvio2 = DetalleEnvio.builder()
                .id(UUID.randomUUID())
                .pedido(pedido2)
                .direccion("Cra Test #Test-02")
                .transportadora("SERVIENTREGA")
                .numeroGuia(986321)
                .build();
    }

    @Test
    void getDetalleEnvioById() {
        DetalleEnvioToShowDto detalleEnvioDTO = new DetalleEnvioToShowDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );
        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioDTO);

        DetalleEnvioToShowDto detalleEnvioFinded = detalleEnvioService.getDetalleEnvioById(detalleEnvio.getId());
        assertThat(detalleEnvioFinded).isNotNull();
        assertThat(detalleEnvioFinded.id()).isEqualTo(detalleEnvio.getId());
    }

    @Test
    void getAllDetalleEnvio() {
        DetalleEnvioToShowDto detalleEnvioDTO = new DetalleEnvioToShowDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );

        DetalleEnvioToShowDto detalleEnvioDTO2 = new DetalleEnvioToShowDto(
                detalleEnvio2.getId(),
                detalleEnvio2.getDireccion(),
                detalleEnvio2.getTransportadora(),
                detalleEnvio2.getNumeroGuia(),
                pedidoToShowDto2
        );

        given(detalleEnvioRepository.findAll()).willReturn(List.of(detalleEnvio,detalleEnvio2));
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioDTO,detalleEnvioDTO2);

        List<DetalleEnvioToShowDto> detalleEnvios = detalleEnvioService.getAllDetalleEnvio();
        assertThat(detalleEnvios).isNotEmpty();
        assertThat(detalleEnvios.get(0)).isEqualTo(detalleEnvioDTO);
        assertThat(detalleEnvios.get(1)).isEqualTo(detalleEnvioDTO2);
    }

    @Test
    void getDetalleEnvioByPedidoId() {
        DetalleEnvioToShowDto detalleEnvioDTO = new DetalleEnvioToShowDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );

        given(detalleEnvioRepository.findByPedidoId(pedido.getId())).willReturn(Optional.ofNullable(detalleEnvio));
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioDTO);

        DetalleEnvioToShowDto detalleEnvioFinded = detalleEnvioService.getDetalleEnvioByPedidoId(pedido.getId());
        assertThat(detalleEnvioFinded).isNotNull();
        assertThat(detalleEnvioFinded.id()).isEqualTo(detalleEnvio.getId());

    }

    @Test
    void getDetalleEnvioByTransportadora() {
        DetalleEnvioToShowDto detalleEnvioDTO = new DetalleEnvioToShowDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );

        DetalleEnvioToShowDto detalleEnvioDTO2 = new DetalleEnvioToShowDto(
                detalleEnvio2.getId(),
                detalleEnvio2.getDireccion(),
                detalleEnvio2.getTransportadora(),
                detalleEnvio2.getNumeroGuia(),
                pedidoToShowDto2
        );

        given(detalleEnvioRepository.findByTransportadoraContainingIgnoreCase(detalleEnvio.getTransportadora())).willReturn(List.of(detalleEnvio));
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioDTO);

        List<DetalleEnvioToShowDto> detalleEnvioFinded = detalleEnvioService.getDetalleEnvioByTransportadora(detalleEnvio.getTransportadora());
        assertThat(detalleEnvioFinded).isNotEmpty();
        assertThat(detalleEnvioFinded).size().isEqualTo(1);
        assertThat(detalleEnvioFinded.get(0).id()).isEqualTo(detalleEnvio.getId());
    }

    @Test
    void saveDetalleEnvio() {
        DetalleEnvioToShowDto detalleEnvioDTO = new DetalleEnvioToShowDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );

        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvioEntity(any())).willReturn(detalleEnvio);
        given(detalleEnvioRepository.save(any())).willReturn(detalleEnvio);
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioDTO);
        DetalleEnvioToSaveDto detalleEnvioToSave = new DetalleEnvioToSaveDto(
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia(),
                pedidoToShowDto
        );
        DetalleEnvioToShowDto detalleEnvioSave = detalleEnvioService.saveDetalleEnvio(detalleEnvioToSave);
        assertThat(detalleEnvioSave).isNotNull();
        assertThat(detalleEnvioSave.id()).isEqualTo(detalleEnvio.getId());
    }

    @Test
    void updateDetalleEnvioById() {
        DetalleEnvio detalleEnvioUpdated = DetalleEnvio.builder()
                .id(detalleEnvio.getId())
                .pedido(detalleEnvio.getPedido())
                .direccion(detalleEnvio.getDireccion())
                .transportadora(detalleEnvio.getTransportadora())
                .numeroGuia(89452)
                .build();

        DetalleEnvioToShowDto detalleEnvioUpdatedDTO = new DetalleEnvioToShowDto(
                detalleEnvioUpdated.getId(),
                detalleEnvioUpdated.getDireccion(),
                detalleEnvioUpdated.getTransportadora(),
                detalleEnvioUpdated.getNumeroGuia(),
                pedidoToShowDto
        );

        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        given(detalleEnvioRepository.save(any())).willReturn(detalleEnvioUpdated);
        given(detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(any())).willReturn(detalleEnvioUpdatedDTO);

        DetalleEnvioToSaveDto detalleEnvioToUpdate = new DetalleEnvioToSaveDto(
                null,
                null,
                detalleEnvioUpdated.getNumeroGuia(),
                new PedidoToShowDto(pedido.getId(),null,null,null)
        );

        DetalleEnvioToShowDto detalleEnvioActualizado = detalleEnvioService.updateDetalleEnvioById(detalleEnvio.getId(),detalleEnvioToUpdate);
        assertThat(detalleEnvioActualizado).isNotNull();
        assertThat(detalleEnvioActualizado.id()).isEqualTo(detalleEnvio.getId());
        assertThat(detalleEnvioActualizado.numeroGuia()).isEqualTo(detalleEnvioUpdated.getNumeroGuia());
    }

    @Test
    void deleteDetalleEnvioById() {
        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        willDoNothing().given(detalleEnvioRepository).deleteById(any());
        detalleEnvioService.deleteDetalleEnvioById(detalleEnvio.getId());
        verify(detalleEnvioRepository,times(1)).deleteById(any());
    }
}