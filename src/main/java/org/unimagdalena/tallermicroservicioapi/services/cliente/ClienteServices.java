package org.unimagdalena.tallermicroservicioapi.services.cliente;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteServices {
    ClienteDto SaveCliente(ClienteToSaveDto cliente);
    ClienteDto updateClienteById(UUID id, ClienteToSaveDto cliente);
    ClienteDto findClienteById(UUID id);
    List<ClienteDto> findAllCliente();
    void deleteClienteById(UUID id);
    ClienteDto findClienteByEmail(String email);
    List<ClienteDto> findClienteByDireccionContainingIgnoreCase(String direccion);
    List<ClienteDto> findClienteByNombreStartingWithIgnoreCase(String nombre);

}
