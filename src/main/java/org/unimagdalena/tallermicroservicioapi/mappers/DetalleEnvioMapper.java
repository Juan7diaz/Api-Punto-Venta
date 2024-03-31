package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {

    DetalleEnvio detalleEnvioDtoToDetalleEnvioEntity(DetalleEnvioDto detalleEnvioDto);

    DetalleEnvioDto detalleEnvioEntityToDetalleEnvioDto(DetalleEnvio detalleEnvio);

    @Mapping(target = "id", ignore = true)
    DetalleEnvio detalleEnvioToSaveDtoToDetalleEnvioEntity(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    DetalleEnvioToSaveDto detalleEnvioEntityToDetalleEnvioToSaveDto(DetalleEnvio detalleEnvio);

    @Mapping( target = "pedido", ignore = true)
    DetalleEnvio detalleEnvioToShowDtoToDetalleEnvioEntity(DetalleEnvioToShowDto detalleEnvioToShowDto);

    DetalleEnvioToShowDto detalleEnvioEntityToDetalleEnvioToShowDto(DetalleEnvio detalleEnvio);

}
