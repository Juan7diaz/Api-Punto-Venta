package org.unimagdalena.tallermicroservicioapi.services.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.mappers.ClienteMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteMapper clienteMapper;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteServiceImpl clienteService;
    Cliente cliente;
    Cliente cliente2;
    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();

        cliente2 = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test2")
                .email("test2@test.com")
                .direccion("Cra Test2 #Test-02")
                .build();
    }

    @Test
    void saveCliente() {
        ClienteToShowDto clienteToShowDto = new ClienteToShowDto(
                cliente.getId(),
                "Test",
                "test@test.com",
                "Cra Test #Test-01"
        );

        given(clienteMapper.clienteToSaveDtoToClienteEntity(any())).willReturn(cliente);
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteToShowDto);
        given(clienteRepository.save(any())).willReturn(cliente);

        ClienteToSaveDto clienteToSave = new ClienteToSaveDto(
                "Test",
                "test@test.com",
                "Cra Test #Test-01"
        );

        ClienteToShowDto clienteSaved = clienteService.SaveCliente(clienteToSave);
        assertThat(clienteSaved).isNotNull();
        assertThat(clienteSaved.id()).isEqualTo(clienteToShowDto.id());
    }

    @Test
    void updateClienteById() {
        ClienteToShowDto clienteToShowDto = new ClienteToShowDto(
                cliente.getId(),
                "Test Updated",
                "test.updated@test.com",
                "Cra Test Updated #Test-01"
        );

        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteToShowDto);

        ClienteToSaveDto clienteToUpdate = new ClienteToSaveDto(
                "Test Updated",
                "test.updated@test.com",
                "Cra Test Updated #Test-01"
        );

        ClienteToShowDto clienteUpdated = clienteService.updateClienteById(cliente.getId(), clienteToUpdate);

        assertThat(clienteUpdated).isNotNull();
        assertThat(clienteUpdated.id()).isEqualTo(clienteToShowDto.id());
        assertThat(clienteUpdated.nombre()).isEqualTo(clienteToShowDto.nombre());
        assertThat(clienteUpdated.email()).isEqualTo(clienteToShowDto.email());
        assertThat(clienteUpdated.direccion()).isEqualTo(clienteToShowDto.direccion());
    }

    @Test
    void findAllCliente() {
        ClienteToShowDto clienteDTO = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        ClienteToShowDto clienteDTO2 = new ClienteToShowDto(
                cliente2.getId(),
                cliente2.getNombre(),
                cliente2.getEmail(),
                cliente2.getDireccion()
        );
        given(clienteRepository.findAll()).willReturn(List.of(cliente,cliente2));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteDTO,clienteDTO2);

        List<ClienteToShowDto> clientes = clienteService.findAllCliente();
        assertThat(clientes).isNotEmpty();
        assertThat(clientes.get(0)).isEqualTo(clienteDTO);
        assertThat(clientes.get(1)).isEqualTo(clienteDTO2);
    }

    @Test
    void findClienteById() {
        ClienteToShowDto clienteFinded = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteFinded);
        ClienteToShowDto clienteEncontrado = clienteService.findClienteById(cliente.getId());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.id()).isEqualTo(cliente.getId());
    }

    @Test
    void deleteClienteById() {
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        willDoNothing().given(clienteRepository).deleteById(any());
        clienteService.deleteClienteById(cliente.getId());
        verify(clienteRepository, times(1)).deleteById(any());
    }

    @Test
    void findClienteByEmail() {
        ClienteToShowDto clienteFinded = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findByEmail(cliente.getEmail())).willReturn(Optional.ofNullable(cliente));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteFinded);
        ClienteToShowDto clienteEncontrado = clienteService.findClienteByEmail(cliente.getEmail());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.id()).isEqualTo(cliente.getId());
    }

    @Test
    void findClienteByDireccionContainingIgnoreCase() {
        ClienteToShowDto clienteFinded = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findByDireccionContainingIgnoreCase(cliente.getDireccion())).willReturn(List.of(cliente));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteFinded);
        List<ClienteToShowDto> clienteEncontrado = clienteService.findClienteByDireccionContainingIgnoreCase(cliente.getDireccion());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.get(0).id()).isEqualTo(cliente.getId());
    }

    @Test
    void findClienteByNombreStartingWithIgnoreCase() {
        String prefixName = "Te";
        ClienteToShowDto clienteFinded = new ClienteToShowDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        ClienteToShowDto clienteFinded2 = new ClienteToShowDto(
                cliente2.getId(),
                cliente2.getNombre(),
                cliente2.getEmail(),
                cliente2.getDireccion()
        );
        given(clienteRepository.findByNombreStartingWithIgnoreCase(prefixName)).willReturn(List.of(cliente,cliente2));
        given(clienteMapper.clienteEntityToclienteToShowDto(any())).willReturn(clienteFinded,clienteFinded2);
        List<ClienteToShowDto> clientesEncontrados = clienteService.findClienteByNombreStartingWithIgnoreCase(prefixName);
        assertThat(clientesEncontrados).isNotEmpty();
        assertThat(clientesEncontrados.get(0).id()).isEqualTo(cliente.getId());
        assertThat(clientesEncontrados.get(1).id()).isEqualTo(cliente2.getId());
    }
}