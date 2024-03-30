package org.unimagdalena.tallermicroservicioapi.services.product;

import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;

import java.util.List;
import java.util.UUID;

public interface ProductServices {

    List<ProductDto> findAllProducts();
    ProductDto findProductById(UUID id);
    List<ProductDto>  findProductByNombre(String nombre);
    List<ProductDto> findProductInStock();
    ProductDto saveProduct(ProductToSaveDto product);
    ProductDto updateProductById(UUID id, ProductToSaveDto product);
    void deleteProductById(UUID id);
}
