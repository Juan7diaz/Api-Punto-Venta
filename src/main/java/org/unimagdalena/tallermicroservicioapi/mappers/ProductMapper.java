package org.unimagdalena.tallermicroservicioapi.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.entities.Product;

@Mapper
public interface ProductMapper {

    Product productDtoToProductEntity(ProductDto productDto);

    ProductDto productEntityToProductDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemsPedidos", ignore = true)
    Product productToSaveDtoToProductEntity(ProductToSaveDto productToSaveDto);

    ProductToSaveDto productEntityToProductToSaveDto(Product product);

}
