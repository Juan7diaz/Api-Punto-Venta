package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.config.CustomMetricsBinder;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.pago.PagoServices;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")

public class PagoController {

    PagoServices pagoServices;
    private final CustomMetricsBinder customMetricsBinder;

    @Autowired
    public PagoController(PagoServices pagoServices, CustomMetricsBinder customMetricsBinder){
        this.pagoServices = pagoServices;
        this.customMetricsBinder=customMetricsBinder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoToShowDto> getPagoById(@PathVariable UUID id){
        try {
            PagoToShowDto res = pagoServices.findPagoById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PagoToShowDto>> getAllPago(){
        try {
            customMetricsBinder.incrementGetCounter();
            List<PagoToShowDto> res = pagoServices.findAllPago();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PagoToShowDto> getPagoByOrderId(@PathVariable UUID orderId, @RequestParam("metodopago") MetodoPago status){
        try {
            PagoToShowDto res = pagoServices.findPagoByPedidoIdAndMetodoPago(orderId, status);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/date-range")
    public ResponseEntity<List<PagoToShowDto>> getPagosByDateRange(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        try {
            List<PagoToShowDto> res = pagoServices.findPagoByFechaPagoBetween(startDate, endDate);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<PagoToShowDto> postPago(@RequestBody PagoToSaveDto pago){
        PagoToShowDto res = pagoServices.savePago(pago);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoToShowDto> putPago(@PathVariable UUID id, @RequestBody PagoToSaveDto pago){
        try{
            PagoToShowDto res = pagoServices.updatePagoById(id, pago);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> putPago(@PathVariable UUID id){
        try{
            pagoServices.deletePagoById(id);
            return ResponseEntity.ok().body("Pago eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
