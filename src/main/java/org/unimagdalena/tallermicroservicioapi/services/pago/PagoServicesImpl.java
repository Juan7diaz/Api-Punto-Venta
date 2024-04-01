package org.unimagdalena.tallermicroservicioapi.services.pago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Pago;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.PagoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.PagoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PagoServicesImpl implements PagoServices{


    PagoRepository pagoRepository;
    PagoMapper pagoMapper;
    PedidoRepository pedidoRepository;

    @Autowired
    public PagoServicesImpl(PagoRepository pagoRepository, PagoMapper pagoMapper, PedidoRepository pedidoRepository ){
        this.pagoMapper = pagoMapper;
        this.pagoRepository = pagoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public PagoToShowDto findPagoById(UUID id) {

        Optional<Pago> pago = pagoRepository.findById(id);

        if(pago.isEmpty())
            throw new NotFoundException("No existe pago con ese Id");

        return pagoMapper.pagoEntityToPagoToShowDto(pago.get());
    }

    @Override
    public List<PagoToShowDto> findAllPago() {

        List<Pago> pagos = pagoRepository.findAll();

        if(pagos.isEmpty())
            throw new NotFoundException("No hay pagos registrado");

        List<PagoToShowDto> pagoADevolver = new ArrayList<>();

        pagos.forEach( pago -> {
            PagoToShowDto p = pagoMapper.pagoEntityToPagoToShowDto(pago);
            pagoADevolver.add(p);
        } );

        return pagoADevolver;
    }

    @Override
    public PagoToShowDto findPagoByPedidoIdAndMetodoPago(UUID pedidoId, MetodoPago metodoPago) {

        Optional<Pago> pagoMatch = pagoRepository.findByPedidoIdAndMetodoPago(pedidoId, metodoPago);

        if(pagoMatch.isEmpty())
            throw new NotFoundException("No hay pago que hagan match con " + pedidoId + " - " + metodoPago);

        return pagoMapper.pagoEntityToPagoToShowDto(pagoMatch.get());
    }

    @Override
    public List<PagoToShowDto> findPagoByFechaPagoBetween(LocalDateTime startDate, LocalDateTime endDate) {

        List<Pago> pagoMatch = pagoRepository.findByFechaPagoBetween(startDate, endDate);

        if(pagoMatch.isEmpty())
            throw new NotFoundException("No hay pago registrado dentro esas fechas ");


        List<PagoToShowDto> pagoADevolver = new ArrayList<>();

        pagoMatch.forEach( pago -> {
            PagoToShowDto p = pagoMapper.pagoEntityToPagoToShowDto(pago);
            pagoADevolver.add(p);
        } );

        return pagoADevolver;
    }

    @Override
    public PagoToShowDto savePago(PagoToSaveDto pagoToSaveDto) {

        UUID pedido_id = pagoToSaveDto.pedido().id();
        Optional<Pedido> pedido = pedidoRepository.findById(pedido_id);

        if(pedido.isEmpty())
            throw new NotFoundException("No se puede registrar un pago a un pedido no existente");

        //verificar si ya existe un pago para ese pedido
        List<Pago> pagos = pagoRepository.findAll();
        for(Pago p : pagos){
            if(p.getPedido().getId().equals(pedido_id))
                throw new NotFoundException("Ya existe un pago para este pedido");
        }

        Pago PagoEntity = pagoMapper.pagoToSaveDtoToPagoEntity(pagoToSaveDto);

        PagoEntity.setPedido(pedido.get());

        Pago pagoGuardado = pagoRepository.save(PagoEntity);

        return pagoMapper.pagoEntityToPagoToShowDto(pagoGuardado);
    }

    @Override
    public PagoToShowDto updatePagoById(UUID pagoId, PagoToSaveDto pagoToSaveDto) {

        Optional<Pago> pago = pagoRepository.findById(pagoId);

        if(pago.isEmpty())
            throw new NotFoundException("No hay pago con ese id " + pagoId);

        if(pagoToSaveDto.fechaPago() != null)
            pago.get().setFechaPago(pagoToSaveDto.fechaPago());

        if(pagoToSaveDto.totalPago() != null)
            pago.get().setTotalPago(pagoToSaveDto.totalPago());

        if(pagoToSaveDto.metodoPago() != null)
            pago.get().setMetodoPago(pagoToSaveDto.metodoPago());

        Pago pagoGuardado = pagoRepository.save(pago.get());

        return pagoMapper.pagoEntityToPagoToShowDto(pagoGuardado);
    }

    @Override
    public void deletePagoById(UUID pagoId) {

        Optional<Pago> pago = pagoRepository.findById(pagoId);

        if(pago.isEmpty())
            throw new NotFoundException("No hay pago con ese id " + pagoId);

        pagoRepository.deleteById(pagoId);

    }
}
