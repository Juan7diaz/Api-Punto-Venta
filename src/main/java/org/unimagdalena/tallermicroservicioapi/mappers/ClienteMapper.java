package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente clienteDtoToClienteEntity(ClienteDto clienteDto);

    @Mapping(target = "pedidos", expression = "java(new ArrayList<>())")
    @Mapping(target = "pedidos.cliente", ignore = true)
    ClienteDto clienteEntityToClienteDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", expression = "java(new ArrayList<>())")
    Cliente clienteToSaveDtoToClienteEntity(ClienteToSaveDto clienteToSaveDto);

    ClienteToSaveDto clienteEntityToClienteToSaveDto(Cliente cliente);

    @Mapping(target = "pedidos", ignore = true)
    Cliente clienteToShowDtoToClienteEntity( ClienteToShowDto clienteToShowDto );

    ClienteToShowDto clienteEntityToclienteToShowDto( Cliente cliente );

}