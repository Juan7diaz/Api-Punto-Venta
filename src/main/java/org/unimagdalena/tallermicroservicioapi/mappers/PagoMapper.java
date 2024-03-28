package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;

@Mapper
public interface PagoMapper {

    Pago pagoDtoToPagoEntity(PagoDto pagoDto);

    PagoDto pagoEntityToPagoDto(Pago pago);

    @Mapping(target = "id", ignore = true)
    Pago pagoToSaveDtoToPagoEntity(PagoToSaveDto pagoToSaveDto);

    PagoToSaveDto pagoEntityToPagoToSaveDto(Pago pago);

}
