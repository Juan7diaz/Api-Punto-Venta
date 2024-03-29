package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.cliente.ClienteServices;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customers")
public class ClienteController {

    private final ClienteServices clienteServices;

    @Autowired
    public ClienteController(ClienteServices clienteServices){
       this.clienteServices = clienteServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable UUID id){
        try {
            ClienteDto res = clienteServices.findClienteById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllCliente(){
        try {
            List<ClienteDto> res = clienteServices.findAllCliente();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDto> getClienteByEmail(@PathVariable String email){
        try {
            ClienteDto res = clienteServices.findClienteByEmail(email);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/city")
    public ResponseEntity<List<ClienteDto>> getClientesByCityName(@RequestParam("cityName") String address){
        try {
            List<ClienteDto> res = clienteServices.findClienteByDireccionContainingIgnoreCase(address);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<ClienteDto> postCliente(@RequestBody ClienteToSaveDto cliente){
        ClienteDto res = clienteServices.SaveCliente(cliente);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> putCliente(@PathVariable UUID id, @RequestBody ClienteToSaveDto cliente){
        try{
            ClienteDto res = clienteServices.updateClienteById(id, cliente);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable UUID id){
        try{
            clienteServices.deleteClienteById(id);
            return ResponseEntity.ok().body("Cliente eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
