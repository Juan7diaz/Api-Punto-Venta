package org.unimagdalena.tallermicroservicioapi.services.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import org.unimagdalena.tallermicroservicioapi.mappers.ProductMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ProductRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProductServicesImplTest {

    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServicesImpl productService;
    Product product;
    Product product2;
    @BeforeEach
    void setUp(){
        product = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product test")
                .price((float) 1000)
                .stock(10)
                .build();
        product2 = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product2 test")
                .price((float) 1500)
                .stock(5)
                .build();
    }

    @Test
    void findAllProducts() {
        ProductToShowDto productDTO = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        ProductToShowDto productDTO2 = new ProductToShowDto(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock()
        );

        given(productRepository.findAll()).willReturn(List.of(product,product2));
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productDTO,productDTO2);

        List<ProductToShowDto> products = productService.findAllProducts();
        assertThat(products).isNotEmpty();
        assertThat(products.get(0)).isEqualTo(productDTO);
        assertThat(products.get(1)).isEqualTo(productDTO2);
    }

    @Test
    void findProductById() {
        ProductToShowDto productFinded = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productFinded);

        ProductToShowDto productEncontrado = productService.findProductById(product.getId());
        assertThat(productEncontrado).isNotNull();
        assertThat(productEncontrado.id()).isEqualTo(product.getId());
    }

    @Test
    void findProductByNombre() {
        ProductToShowDto productFinded = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        given(productRepository.findByNombreContainingIgnoreCase(product.getNombre())).willReturn(List.of(product));
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productFinded);

        List<ProductToShowDto> productsEncontrados = productService.findProductByNombre(product.getNombre());
        assertThat(productsEncontrados).isNotEmpty();
        assertThat(productsEncontrados).size().isEqualTo(1);
        assertThat(productsEncontrados.get(0).id()).isEqualTo(product.getId());
    }

    @Test
    void findProductInStock() {
        ProductToShowDto productFinded = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        ProductToShowDto productFinded2 = new ProductToShowDto(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock()
        );

        given(productRepository.findInStock()).willReturn(List.of(product,product2));
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productFinded,productFinded2);

        List<ProductToShowDto> productsEncontrados = productService.findProductInStock();
        assertThat(productsEncontrados).isNotEmpty();
        assertThat(productsEncontrados).size().isEqualTo(2);
        assertThat(productsEncontrados.get(0).id()).isEqualTo(product.getId());
        assertThat(productsEncontrados.get(1).id()).isEqualTo(product2.getId());
    }

    @Test
    void saveProduct() {
        ProductToShowDto productDTO = new ProductToShowDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());

        given(productMapper.productToSaveDtoToProductEntity(any())).willReturn(product);
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productDTO);
        given(productRepository.save(any())).willReturn(product);

        ProductToSaveDto productToSave = new ProductToSaveDto(
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );

        ProductToShowDto productSaved = productService.saveProduct(productToSave);
        assertThat(productSaved).isNotNull();
        assertThat(productSaved.id()).isEqualTo(product.getId());
    }

    @Test
    void updateProductById() {
        Product productUpdated = Product.builder()
                .id(product.getId())
                .nombre(product.getNombre())
                .price((float) 800)
                .stock(product.getStock())
                .build();

        ProductToShowDto productUpdatedDTO = new ProductToShowDto(
                productUpdated.getId(),
                productUpdated.getNombre(),
                productUpdated.getPrice(),
                productUpdated.getStock()
        );

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productRepository.save(any())).willReturn(productUpdated);
        given(productMapper.productEntityToProductToShowDto(any())).willReturn(productUpdatedDTO);

        ProductToSaveDto productToUpdate = new ProductToSaveDto(
                null,
                productUpdated.getPrice(),
                null);

        ProductToShowDto productActualizado = productService.updateProductById(product.getId(),productToUpdate);

        assertThat(productActualizado).isNotNull();
        assertThat(productActualizado.id()).isEqualTo(product.getId());
        assertThat(productActualizado.price()).isEqualTo(productToUpdate.price());
    }

    @Test
    void deleteProductById() {
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        willDoNothing().given(productRepository).deleteById(any());
        productService.deleteProductById(product.getId());
        verify(productRepository,times(1)).deleteById(any());
    }

}