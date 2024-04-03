package org.unimagdalena.tallermicroservicioapi.services.cliente;

import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;

import java.util.List;
import java.util.UUID;

public interface ClienteServices {
    ClienteToShowDto SaveCliente(ClienteToSaveDto cliente);
    ClienteToShowDto updateClienteById(UUID id, ClienteToSaveDto cliente);
    ClienteToShowDto findClienteById(UUID id);
    List<ClienteToShowDto> findAllCliente();
    void deleteClienteById(UUID id);
    ClienteToShowDto findClienteByEmail(String email);
    List<ClienteToShowDto> findClienteByDireccionContainingIgnoreCase(String direccion);
    List<ClienteToShowDto> findClienteByNombreStartingWithIgnoreCase(String nombre);

}
