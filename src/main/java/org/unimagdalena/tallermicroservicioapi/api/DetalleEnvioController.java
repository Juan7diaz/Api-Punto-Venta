package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.detalleEnvio.DetalleEnvioServices;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/shipping")
public class DetalleEnvioController {

    private final DetalleEnvioServices detalleEnvioServices;

    @Autowired
    public DetalleEnvioController(DetalleEnvioServices detalleEnvioServices) {
        this.detalleEnvioServices = detalleEnvioServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioToShowDto> getDetalleEnvioById(@PathVariable UUID id) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioServices.getDetalleEnvioById(id);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DetalleEnvioToShowDto>> getAllDetalleEnvio() {
        try {
            List<DetalleEnvioToShowDto> detallesEnvio = detalleEnvioServices.getAllDetalleEnvio();
            return ResponseEntity.ok().body(detallesEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DetalleEnvioToShowDto> getDetalleEnvioByPedidoId(@PathVariable UUID orderId) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioServices.getDetalleEnvioByPedidoId(orderId);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carrier")
    public ResponseEntity<List<DetalleEnvioToShowDto>> getDetalleEnvioByTransportadora(@RequestParam("name") String transporter) {
        try {
            List<DetalleEnvioToShowDto> detallesEnvio = detalleEnvioServices.getDetalleEnvioByTransportadora(transporter);
            return ResponseEntity.ok().body(detallesEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleEnvioToShowDto> postDetalleEnvio(@RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        DetalleEnvioToShowDto detalleEnvio = detalleEnvioServices.saveDetalleEnvio(detalleEnvioToSaveDto);
        return ResponseEntity.ok().body(detalleEnvio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioToShowDto> putDetalleEnvio(@PathVariable UUID id, @RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioServices.updateDetalleEnvioById(id, detalleEnvioToSaveDto);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalleEnvio(@PathVariable UUID id) {
        try {
            detalleEnvioServices.deleteDetalleEnvioById(id);
            return ResponseEntity.ok().body("Detalle de envío eliminado");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
