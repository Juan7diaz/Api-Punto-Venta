package org.unimagdalena.tallermicroservicioapi.services.pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.mappers.PedidoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ClienteRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
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
class PedidoServicesImplTest {

    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private PedidoServicesImpl pedidoService;
    Pedido pedido;
    Pedido pedido2;
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
        pedido = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status(EstadoPedido.PENDIENTE)
                .build();
        pedido2 = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.of(2024,3,20,15,0))
                .status(EstadoPedido.PENDIENTE)
                .build();
        clienteToShowDto = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
    }

    @Test
    void savePedido() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(pedidoMapper.pedidoToSaveDtoToPedido(any())).willReturn(pedido);
        given(pedidoRepository.save(any())).willReturn(pedido);
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO);

        PedidoToSaveDto pedidoToSave = new PedidoToSaveDto(
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        PedidoToShowDto pedidoSaved = pedidoService.savePedido(pedidoToSave);
        assertThat(pedidoSaved).isNotNull();
        assertThat(pedidoSaved.id()).isEqualTo(pedido.getId());
        assertThat(pedidoSaved.cliente().id()).isEqualTo(cliente.getId());
    }

    @Test
    void updatePedidoById() {
        Pedido pedidoUpdated = Pedido.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente())
                .fechaPedido(pedido.getFechaPedido())
                .status(EstadoPedido.ENVIADO)
                .build();
        PedidoToShowDto pedidoUpdatedDTO = new PedidoToShowDto(
                pedido.getId(),
                pedidoUpdated.getFechaPedido(),
                pedidoUpdated.getStatus(),
                clienteToShowDto
        );
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pedidoRepository.save(any())).willReturn(pedidoUpdated);
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoUpdatedDTO);

        PedidoToSaveDto pedidoToUpdate = new PedidoToSaveDto(
                pedido.getFechaPedido(),
                EstadoPedido.ENVIADO,
                new ClienteToShowDto(cliente.getId(),null,null,null)
        );

        PedidoToShowDto pedidoActualizado = pedidoService.updatePedidoById(pedido.getId(),pedidoToUpdate);
        assertThat(pedidoActualizado).isNotNull();
        assertThat(pedidoActualizado.id()).isEqualTo(pedido.getId());
        assertThat(pedidoActualizado.status()).isEqualTo(pedidoToUpdate.status());
    }

    @Test
    void findPedidoById() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO);
        PedidoToShowDto pedidoFinded = pedidoService.findPedidoById(pedido.getId());
        assertThat(pedidoFinded).isNotNull();
        assertThat(pedidoFinded.id()).isEqualTo(pedido.getId());
    }

    @Test
    void findAllPedidos() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );
        PedidoToShowDto pedidoDTO2 = new PedidoToShowDto(
                pedido2.getId(),
                pedido2.getFechaPedido(),
                pedido2.getStatus(),
                clienteToShowDto
        );

        given(pedidoRepository.findAll()).willReturn(List.of(pedido,pedido2));
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO,pedidoDTO2);
        List<PedidoToShowDto> pedidos = pedidoService.findAllPedidos();
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos.get(0)).isEqualTo(pedidoDTO);
        assertThat(pedidos.get(1)).isEqualTo(pedidoDTO2);
    }

    @Test
    void deletePedidoById() {
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        willDoNothing().given(pedidoRepository).deleteById(any());
        pedidoService.deletePedidoById(pedido.getId());
        verify(pedidoRepository,times(1)).deleteById(any());
    }

    @Test
    void findPedidosByFechaPedidoBetween() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );
        PedidoToShowDto pedidoDTO2 = new PedidoToShowDto(
                pedido2.getId(),
                pedido2.getFechaPedido(),
                pedido2.getStatus(),
                clienteToShowDto
        );

        LocalDateTime startDate = LocalDateTime.of(2024,3,15,8,30);
        LocalDateTime endDate = LocalDateTime.of(2024,3,25,20,0);

        given(pedidoRepository.findByFechaPedidoBetween(startDate,endDate)).willReturn(List.of(pedido2));
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO2);

        List<PedidoToShowDto> pedidosFinded = pedidoService.findPedidosByFechaPedidoBetween(startDate,endDate);
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(1);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido2.getId());
    }

    @Test
    void findPedidosByClienteIdAndStatus() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        PedidoToShowDto pedidoDTO2 = new PedidoToShowDto(
                pedido2.getId(),
                pedido2.getFechaPedido(),
                EstadoPedido.ENTREGADO,
                clienteToShowDto
        );
        given(pedidoRepository.findByClienteIdAndStatus(pedido.getCliente().getId(),pedido.getStatus())).willReturn(List.of(pedido));
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO);
        List<PedidoToShowDto> pedidosFinded = pedidoService.findPedidosByClienteIdAndStatus(pedido.getCliente().getId(),pedido.getStatus());
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(1);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido.getId());
    }

    @Test
    void findPedidosByClienteIdWithItemsFetch() {
        PedidoToShowDto pedidoDTO = new PedidoToShowDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getStatus(),
                clienteToShowDto
        );

        PedidoToShowDto pedidoDTO2 = new PedidoToShowDto(
                pedido2.getId(),
                pedido2.getFechaPedido(),
                pedido2.getStatus(),
                clienteToShowDto
        );

        given(pedidoRepository.findByClienteIdWithItemsFetch(pedido.getCliente().getId())).willReturn(List.of(pedido,pedido2));
        given(pedidoMapper.pedidoEntityToPedidoToShow(any())).willReturn(pedidoDTO,pedidoDTO2);
        List<PedidoToShowDto> pedidosFinded = pedidoService.findPedidosByClienteIdWithItemsFetch(pedido.getCliente().getId());
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(2);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido.getId());
        assertThat(pedidosFinded.get(1).id()).isEqualTo(pedido2.getId());
    }
}