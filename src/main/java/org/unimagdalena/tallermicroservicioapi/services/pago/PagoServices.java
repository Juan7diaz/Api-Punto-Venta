package org.unimagdalena.tallermicroservicioapi.services.pago;

import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PagoServices {

    PagoToShowDto  findPagoById(UUID id);

    List<PagoToShowDto> findAllPago();

    PagoToShowDto findPagoByPedidoIdAndMetodoPago(UUID PedidoId, MetodoPago metodoPago);

    List<PagoToShowDto> findPagoByFechaPagoBetween(LocalDateTime startDate, LocalDateTime endDate);

    PagoToShowDto savePago(PagoToSaveDto pagoToSaveDto);

    PagoToShowDto updatePagoById(UUID pagoId, PagoToSaveDto pagoToSaveDto);

    void deletePagoById(UUID pagoId);

}
