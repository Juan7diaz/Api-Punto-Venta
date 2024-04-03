package org.unimagdalena.tallermicroservicioapi.mappers;

import org.mapstruct.Mapper;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    Pago pagoDtoToPagoEntity(PagoDto pagoDto);

    PagoDto pagoEntityToPagoDto(Pago pago);

    Pago pagoToSaveDtoToPagoEntity(PagoToSaveDto pagoToSaveDto);

    PagoToSaveDto pagoEntityToPagoToSaveDto(Pago pago);

    Pago pagoToShowDtoToPagoEntity(PagoToShowDto pagoToShowDto);

    PagoToShowDto pagoEntityToPagoToShowDto(Pago pago);

}
