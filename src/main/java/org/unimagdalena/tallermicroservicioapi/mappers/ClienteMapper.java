package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;

@Mapper
public interface ClienteMapper {

    Cliente clienteDtoToClienteEntity(ClienteDto clienteDto);

    ClienteDto clienteEntityToClienteDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    Cliente clienteToSaveDtoToClienteEntity(ClienteToSaveDto clienteToSaveDto);

    ClienteToSaveDto clienteEntityToClienteToSaveDto(Cliente cliente);

}