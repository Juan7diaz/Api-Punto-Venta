package org.unimagdalena.tallermicroservicioapi.services.detalleEnvio;

import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;

import java.util.List;
import java.util.UUID;

public interface DetalleEnvioServices {

    DetalleEnvioToShowDto getDetalleEnvioById(UUID id);

    List<DetalleEnvioToShowDto> getAllDetalleEnvio();

    DetalleEnvioToShowDto getDetalleEnvioByPedidoId(UUID pedido_id);

    List<DetalleEnvioToShowDto> getDetalleEnvioByTransportadora(String transportadora);

    DetalleEnvioToShowDto saveDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    DetalleEnvioToShowDto updateDetalleEnvioById(UUID id, DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    void deleteDetalleEnvioById(UUID id);

}
