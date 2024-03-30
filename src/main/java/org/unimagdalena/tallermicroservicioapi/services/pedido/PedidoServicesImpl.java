package org.unimagdalena.tallermicroservicioapi.services.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.PedidoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ClienteRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServicesImpl implements PedidoServices{


    PedidoRepository pedidoRepository;
    PedidoMapper pedidoMapper;
    ClienteRepository clienteRepository;

    @Autowired
    public PedidoServicesImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ClienteRepository clienteRepository){
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public PedidoDto savePedido(PedidoToSaveDto pedido) {
        Pedido pedidoToSave = pedidoMapper.pedidoToSaveDtoToPedido(pedido);
        Pedido pedidoGuardado = pedidoRepository.save(pedidoToSave);
        return pedidoMapper.pedidoEntityToPedidoDto(pedidoGuardado);
    }

    @Override
    public PedidoDto updatePedidoById(UUID id, PedidoToSaveDto pedido) {
        Optional<Pedido> pedidoConsultado = pedidoRepository.findById(id);

        if(pedidoConsultado.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");

        Pedido pd = pedidoConsultado.get();

        if(pedido.fechaPedido() != null) pd.setFechaPedido(pedido.fechaPedido());
        if(pedido.status()!= null) pd.setStatus(pedido.status());

        Optional<Cliente> clienteEncontrado = clienteRepository.findById(pedido.cliente().id());
        clienteEncontrado.ifPresent(pd::setCliente);

        Pedido pedidoActualizado = pedidoRepository.save(pd);

        return pedidoMapper.pedidoEntityToPedidoDto(pedidoActualizado);
    }

    @Override
    public PedidoDto findPedidoById(UUID id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if(pedido.isEmpty())
            throw new NotFoundException("Pedido con ID " + id + " no encontrado");

        return pedidoMapper.pedidoEntityToPedidoDto(pedido.get());
    }

    @Override
    public List<PedidoDto> findAllPedidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        if(pedidos.isEmpty())
            throw new NotFoundException("No se ha encontrado pedidos");

        List<PedidoDto> allPedidos = new ArrayList<>();

        pedidos.forEach( pedido -> {
            PedidoDto p = pedidoMapper.pedidoEntityToPedidoDto(pedido);
            allPedidos.add(p);
        });

        return allPedidos;
    }

    @Override
    public void deletePedidoById(UUID id) {
        Optional<Pedido> pedidoAEliminar = pedidoRepository.findById(id);

        if(pedidoAEliminar.isEmpty())
            throw new NotFoundException("Pedid con ID " + id + " no existe");

        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoDto> findPedidosByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Pedido> pedidosMatch = pedidoRepository.findByFechaPedidoBetween(startDate, endDate);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró pedidos dentro esas fechas");

        List<PedidoDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoDto(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;
    }

    @Override
    public List<PedidoDto> findPedidosByClienteIdAndStatus(UUID cliente_id, EstadoPedido status) {

        List<Pedido> pedidosMatch = pedidoRepository.findByClienteIdAndStatus(cliente_id, status);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró ningún pedido con ese id y ese status");

        List<PedidoDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoDto(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;

    }

    @Override
    public List<PedidoDto> findPedidosByClienteIdWithItemsFetch(UUID cliente_id) {

        List<Pedido> pedidosMatch = pedidoRepository.findByClienteIdWithItemsFetch(cliente_id);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró ningún pedido que haga match");

        List<PedidoDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoDto(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;
    }
}
