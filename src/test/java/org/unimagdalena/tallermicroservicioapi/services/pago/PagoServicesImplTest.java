package org.unimagdalena.tallermicroservicioapi.services.pago;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.mappers.PagoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.PagoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

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
class PagoServicesImplTest {

    @Mock
    private PagoMapper pagoMapper;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private PagoServicesImpl pagoService;
    Pago pago;
    Pago pago2;
    Pedido pedido;
    PedidoToShowDto pedidoToShowDto;
    Pedido pedido2;
    PedidoToShowDto pedidoToShowDto2;
    Cliente cliente;
    ClienteToShowDto clienteToShowDto;


    @BeforeEach
    void setUp() {
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

        pago = Pago.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .totalPago(1000)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.NEQUI)
                .build();

        pago2 = Pago.builder()
                .id(UUID.randomUUID())
                .pedido(pedido2)
                .totalPago(1500)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.DAVIPLATA)
                .build();
    }

    @Test
    void findPagoById() {
        PagoToShowDto pagoDTO = new PagoToShowDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );
        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoDTO);
        PagoToShowDto pagoFinded = pagoService.findPagoById(pago.getId());
        assertThat(pagoFinded).isNotNull();
        assertThat(pagoFinded.id()).isEqualTo(pago.getId());

    }

    @Test
    void findAllPago() {
        PagoToShowDto pagoDTO = new PagoToShowDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );
        PagoToShowDto pagoDTO2 = new PagoToShowDto(
                pago2.getId(),
                pago2.getTotalPago(),
                pago2.getFechaPago(),
                pago2.getMetodoPago(),
                pedidoToShowDto2
        );

        given(pagoRepository.findAll()).willReturn(List.of(pago,pago2));
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoDTO,pagoDTO2);

        List<PagoToShowDto> pagos = pagoService.findAllPago();
        assertThat(pagos).isNotEmpty();
        assertThat(pagos.get(0)).isEqualTo(pagoDTO);
        assertThat(pagos.get(1)).isEqualTo(pagoDTO2);
    }

    @Test
    void findPagoByPedidoIdAndMetodoPago() {
        PagoToShowDto pagoDTO = new PagoToShowDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );

        given(pagoRepository.findByPedidoIdAndMetodoPago(pedido.getId(),pago.getMetodoPago())).willReturn(Optional.ofNullable(pago));
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoDTO);
        PagoToShowDto pagoFinded = pagoService.findPagoByPedidoIdAndMetodoPago(pedido.getId(),pago.getMetodoPago());
        assertThat(pagoFinded).isNotNull();
        assertThat(pagoFinded.id()).isEqualTo(pago.getId());
    }

    @Test
    void findPagoByFechaPagoBetween() {
        PagoToShowDto pagoDTO = new PagoToShowDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );

        PagoToShowDto pagoDTO2 = new PagoToShowDto(
                pago2.getId(),
                pago2.getTotalPago(),
                pago2.getFechaPago(),
                pago2.getMetodoPago(),
                pedidoToShowDto2
        );

        LocalDateTime startDate = LocalDateTime.of(2024,3,30,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2024,4,5,0,0,0);
        given(pagoRepository.findByFechaPagoBetween(startDate,endDate)).willReturn(List.of(pago,pago2));
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoDTO,pagoDTO2);

        List<PagoToShowDto> pagosFinded = pagoService.findPagoByFechaPagoBetween(startDate,endDate);
        assertThat(pagosFinded).isNotEmpty();
        assertThat(pagosFinded).size().isEqualTo(2);
        assertThat(pagosFinded.get(0).id()).isEqualTo(pago.getId());
        assertThat(pagosFinded.get(1).id()).isEqualTo(pago2.getId());
    }

    @Test
    void savePago() {
        PagoToShowDto pagoDTO = new PagoToShowDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );

        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pagoMapper.pagoToSaveDtoToPagoEntity(any())).willReturn(pago);
        given(pagoRepository.save(any())).willReturn(pago);
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoDTO);

        PagoToSaveDto pagoToSave = new PagoToSaveDto(
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pedidoToShowDto
        );

        PagoToShowDto pagoSaved = pagoService.savePago(pagoToSave);
        assertThat(pagoSaved).isNotNull();
        assertThat(pagoSaved.id()).isEqualTo(pago.getId());
    }

    @Test
    void updatePagoById() {
        Pago pagoUpdated = Pago.builder()
                .id(pago.getId())
                .pedido(pago.getPedido())
                .totalPago(1250)
                .fechaPago(pago.getFechaPago())
                .metodoPago(pago.getMetodoPago())
                .build();

        PagoToShowDto pagoUpdatedDTO = new PagoToShowDto(
                pagoUpdated.getId(),
                pagoUpdated.getTotalPago(),
                pagoUpdated.getFechaPago(),
                pagoUpdated.getMetodoPago(),
                pedidoToShowDto
        );

        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        given(pagoRepository.save(any())).willReturn(pagoUpdated);
        given(pagoMapper.pagoEntityToPagoToShowDto(any())).willReturn(pagoUpdatedDTO);


        PagoToSaveDto pagoToUpdate = new PagoToSaveDto(
                pagoUpdated.getTotalPago(),
                null,
                null,
                new PedidoToShowDto(pedido.getId(),null,null,null)
        );

        PagoToShowDto pagoActualizado = pagoService.updatePagoById(pago.getId(),pagoToUpdate);
        assertThat(pagoActualizado).isNotNull();
        assertThat(pagoActualizado.id()).isEqualTo(pago.getId());
        assertThat(pagoActualizado.totalPago()).isEqualTo(pagoToUpdate.totalPago());
    }

    @Test
    void deletePagoById() {
        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        willDoNothing().given(pagoRepository).deleteById(any());
        pagoService.deletePagoById(pago.getId());
        verify(pagoRepository,times(1)).deleteById(any());
    }
}