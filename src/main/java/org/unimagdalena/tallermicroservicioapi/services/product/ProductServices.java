package org.unimagdalena.tallermicroservicioapi.services.product;

import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;

import java.util.List;
import java.util.UUID;

public interface ProductServices {

    List<ProductToShowDto> findAllProducts();
    ProductToShowDto findProductById(UUID id);
    List<ProductToShowDto>  findProductByNombre(String nombre);
    List<ProductToShowDto> findProductInStock();
    ProductToShowDto saveProduct(ProductToSaveDto product);
    ProductToShowDto updateProductById(UUID id, ProductToSaveDto product);
    void deleteProductById(UUID id);
}
