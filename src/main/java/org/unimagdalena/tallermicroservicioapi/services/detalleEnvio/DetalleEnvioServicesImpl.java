package org.unimagdalena.tallermicroservicioapi.services.detalleEnvio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.DetalleEnvio;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.DetalleEnvioMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ClienteRepository;
import org.unimagdalena.tallermicroservicioapi.repository.DetalleEnvioRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DetalleEnvioServicesImpl implements DetalleEnvioServices {

    DetalleEnvioMapper detalleEnvioMapper;
    DetalleEnvioRepository detalleEnvioRepository;

    PedidoRepository pedidoRepository;

    @Autowired
    public DetalleEnvioServicesImpl(DetalleEnvioMapper detalleEnvioMapper, DetalleEnvioRepository detalleEnvioRepository, PedidoRepository pedidoRepository){
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public DetalleEnvioToShowDto getDetalleEnvioById(UUID id) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio con ese id " + id);

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleEnvio.get());
    }

    @Override
    public List<DetalleEnvioToShowDto> getAllDetalleEnvio() {

        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findAll();

        if(detallesEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio");

        List<DetalleEnvioToShowDto> detalle = new ArrayList<>();

        detallesEnvio.forEach( d -> {
            DetalleEnvioToShowDto d2 = detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(d);
            detalle.add(d2);
        });

        return detalle;
    }

    @Override
    public DetalleEnvioToShowDto getDetalleEnvioByPedidoId(UUID pedido_id) {

        Optional<DetalleEnvio> detalle = detalleEnvioRepository.findByPedidoId(pedido_id);

        if(detalle.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio para ese pedido");

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalle.get());
    }

    @Override
    public List<DetalleEnvioToShowDto> getDetalleEnvioByTransportadora(String transportadora) {

        List<DetalleEnvio> detalles = detalleEnvioRepository.findByTransportadoraContainingIgnoreCase(transportadora);

        if(detalles.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio para la transportadora " + transportadora);

        List<DetalleEnvioToShowDto> detalle = new ArrayList<>();

        detalles.forEach( d -> {
            DetalleEnvioToShowDto d2 = detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(d);
            detalle.add(d2);
        });

        return detalle;
    }

    @Override
    public DetalleEnvioToShowDto saveDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        DetalleEnvio detalleEnvio = detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvioEntity(detalleEnvioToSaveDto);

        Optional<Pedido> pedido = pedidoRepository.findById(detalleEnvioToSaveDto.pedido().id());
        if(  pedido.isEmpty() )
            throw new NotFoundException("No se ha encontrado el pedido");

        detalleEnvio.setPedido(pedido.get());

        DetalleEnvio detalleGuardado = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleGuardado);
    }

    @Override
    public DetalleEnvioToShowDto updateDetalleEnvioById(UUID id, DetalleEnvioToSaveDto detalleEnvioToSaveDto) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio con ese id " + id);

        if(detalleEnvioToSaveDto.numeroGuia() != null )
            detalleEnvio.get().setNumeroGuia(detalleEnvioToSaveDto.numeroGuia());

        if(detalleEnvioToSaveDto.direccion() != null)
            detalleEnvio.get().setDireccion(detalleEnvioToSaveDto.direccion());

        if(detalleEnvioToSaveDto.transportadora() != null)
            detalleEnvio.get().setTransportadora(detalleEnvioToSaveDto.transportadora());

        DetalleEnvio detalleGuardado = detalleEnvioRepository.save(detalleEnvio.get());

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleGuardado);
    }

    @Override
    public void deleteDetalleEnvioById(UUID id) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio con ese id " + id);

        detalleEnvioRepository.deleteById(id);

    }

}
