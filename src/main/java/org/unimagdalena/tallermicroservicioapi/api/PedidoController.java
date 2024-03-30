package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.pedido.PedidoServices;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class PedidoController {

    private final PedidoServices pedidoServices;

    @Autowired
    public  PedidoController(PedidoServices pedidoServices){
        this.pedidoServices = pedidoServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedidoById(@PathVariable UUID id){
        try{
            PedidoDto res = pedidoServices.findPedidoById(id);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedido(){
        try{
            List<PedidoDto> res = pedidoServices.findAllPedidos();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PedidoDto>> getPedidoByClienteIdAndStatus(@PathVariable UUID customerId, @RequestParam("status") String status){
        try{
            EstadoPedido statusPedido = EstadoPedido.fromString(status.toUpperCase());
            List<PedidoDto> res = pedidoServices.findPedidosByClienteIdAndStatus(customerId, statusPedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PedidoDto>> getPedidoBetweenDates(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        try{
            List<PedidoDto> res = pedidoServices.findPedidosByFechaPedidoBetween(startDate, endDate);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDto> postPedido(@RequestBody PedidoToSaveDto pedido){
        try{
            PedidoDto res = pedidoServices.savePedido(pedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<PedidoDto> putPedido(@PathVariable UUID id, @RequestBody PedidoToSaveDto pedido){
        try{
            PedidoDto res = pedidoServices.updatePedidoById(id, pedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> putPedido(@PathVariable UUID id){
        try{
            pedidoServices.deletePedidoById(id);
            return ResponseEntity.ok().body("Pedido Eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
