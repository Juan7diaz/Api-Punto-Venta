package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.itemPedido.ItemPedidoServices;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order-items")
public class ItemPedidoController {

    ItemPedidoServices itemPedidoServices;

    @Autowired
    public ItemPedidoController(ItemPedidoServices itemPedidoServices){
        this.itemPedidoServices = itemPedidoServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoToShowDto> getItemPedidoById(@PathVariable UUID id){
        try {
            ItemPedidoToShowDto res = itemPedidoServices.getItemPedidoById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

   @GetMapping
    public ResponseEntity<List<ItemPedidoToShowDto>> getAllItemPedido(){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoServices.getAllItemPedido();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemPedidoToShowDto>> getItemPedidoByOrderId(@PathVariable UUID orderId){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoServices.findItemPedidoToShowDtoByPedidoId(orderId);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ItemPedidoToShowDto>> getItemPedidoByProductId(@PathVariable UUID productId){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoServices.findItemPedidoToShowDtoByProductId(productId);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ItemPedidoToShowDto> postItemPedido(@RequestBody ItemPedidoToSaveDto itemPedido){
        try{
            ItemPedidoToShowDto res = itemPedidoServices.saveItemPedido(itemPedido);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoToShowDto> putItemPedido(@PathVariable UUID id, @RequestBody ItemPedidoToSaveDto itemPedido){
        try{
            ItemPedidoToShowDto res = itemPedidoServices.updateItemPedido(id, itemPedido);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemPedido(@PathVariable UUID id){
        try{
            itemPedidoServices.deleteItemPedidoById(id);
            return ResponseEntity.ok().body("ItemPedido con ID " + id + " eliminado");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
