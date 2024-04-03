package org.unimagdalena.tallermicroservicioapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.services.product.ProductServices;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductServices productServices;

    @Autowired
    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductToShowDto> getProductById(@PathVariable UUID id) {
        try{
            ProductToShowDto res = productServices.findProductById(id);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductToShowDto>> getAllProduct() {
        try{
            List<ProductToShowDto> res = productServices.findAllProducts();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductToShowDto>> getProductByNombre(@RequestParam String searchTerm) {
        try{
            List<ProductToShowDto> res = productServices.findProductByNombre(searchTerm);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/instock")
    public ResponseEntity<List<ProductToShowDto>> getProductsInStock() {
        try{
            List<ProductToShowDto> res = productServices.findProductInStock();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductToShowDto> saveProduct(@RequestBody ProductToSaveDto product) {
        try {
            ProductToShowDto res = productServices.saveProduct(product);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductToShowDto> updateProduct(@PathVariable UUID id, @RequestBody ProductToSaveDto product) {
        try{
            ProductToShowDto updatedProduct = productServices.updateProductById(id, product);
            return ResponseEntity.ok(updatedProduct);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        try{
            productServices.deleteProductById(id);
            return ResponseEntity.ok().body("Producto Eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
