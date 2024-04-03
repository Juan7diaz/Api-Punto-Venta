package org.unimagdalena.tallermicroservicioapi.services.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.ClienteMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteServices {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper){
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteToShowDto SaveCliente(ClienteToSaveDto cliente) {
        Cliente clienteToSave = clienteMapper.clienteToSaveDtoToClienteEntity(cliente);
        Cliente clienteGuardado = clienteRepository.save(clienteToSave);
        return clienteMapper.clienteEntityToclienteToShowDto(clienteGuardado);
    }

    @Override
    public ClienteToShowDto updateClienteById(UUID id, ClienteToSaveDto cliente) {
        Optional<Cliente> clienteConsultado = clienteRepository.findById(id);

        if(clienteConsultado.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");

        Cliente cl = clienteConsultado.get();

        if (cliente.nombre() != null) cl.setNombre(cliente.nombre());
        if (cliente.email() != null) cl.setEmail(cliente.email());
        if (cliente.direccion() != null) cl.setDireccion(cliente.direccion());

        Cliente clienteActualizado = clienteRepository.save(cl);

        return clienteMapper.clienteEntityToclienteToShowDto(clienteActualizado);
    }

    @Override
    public List<ClienteToShowDto> findAllCliente() {

        List<Cliente> clientes = clienteRepository.findAll();

        if(clientes.isEmpty())
            throw new NotFoundException("No se ha encontrado cliente");

        List<ClienteToShowDto> allCliente =  new ArrayList<>();

        clientes.forEach( cliente -> {
            ClienteToShowDto c = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            allCliente.add(c);
        });

        return allCliente;
    }

    @Override
    public ClienteToShowDto findClienteById(UUID id) {

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");

        return clienteMapper.clienteEntityToclienteToShowDto(cliente.get());
    }

    @Override
    public void deleteClienteById(UUID id) {
        Optional<Cliente> clienteAEliminar = clienteRepository.findById(id);

        if(clienteAEliminar.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no existe");

        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteToShowDto findClienteByEmail(String email) {

        Optional<Cliente> clienteMatch = clienteRepository.findByEmail(email);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontró cliente asociado al email");

        return clienteMapper.clienteEntityToclienteToShowDto(clienteMatch.get());
    }

    @Override
    public List<ClienteToShowDto> findClienteByDireccionContainingIgnoreCase(String direccion) {

        List<Cliente> clienteMatch = clienteRepository.findByDireccionContainingIgnoreCase(direccion);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontró direcciones que hagan match con "+ direccion);

        List<ClienteToShowDto> clienteARegresar =  new ArrayList<>();

        clienteMatch.forEach( cliente -> {
            ClienteToShowDto clienteMappeado = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            clienteARegresar.add(clienteMappeado);
        });

        return clienteARegresar;
    }

    @Override
    public List<ClienteToShowDto> findClienteByNombreStartingWithIgnoreCase(String nombre) {
        List<Cliente> clienteMatch = clienteRepository.findByNombreStartingWithIgnoreCase(nombre);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontró cliente que inicie con " + nombre);

        List<ClienteToShowDto> clienteARegresar =  new ArrayList<>();

        clienteMatch.forEach( cliente -> {
            ClienteToShowDto clienteMappeado = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            clienteARegresar.add(clienteMappeado);
        });

        return clienteARegresar;
    }

}
