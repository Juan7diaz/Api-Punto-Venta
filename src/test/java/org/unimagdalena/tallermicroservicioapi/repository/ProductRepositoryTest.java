package org.unimagdalena.tallermicroservicioapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ProductRepositoryTest extends AbstractIntegrationDBTest {

    ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    Product productoGlobalIphone;
    Product productoGlobalBolso;
    Product productoGlobalXiaomi;
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productoGlobalIphone = Product.builder()
                .nombre("celular iphone 13 pro")
                .price(2500000f)
                .stock(23)
                .build();
        productoGlobalBolso = Product.builder()
                .nombre("Bolso Gucci ltd 23")
                .price(3550000f)
                .stock(0)
                .build();
        productoGlobalXiaomi = Product.builder()
                .nombre("celular xiaomi 23 lite pro")
                .price(950000f)
                .stock(15)
                .build();
        productRepository.save(productoGlobalIphone);
        productRepository.save(productoGlobalBolso);
        productRepository.save(productoGlobalXiaomi);

    }

    @Test
    @DisplayName("[findByNombreContainingIgnoreCase] dado un termino de busqueda, retornar los productos que coincida con dicho termino")
    void test_findByNombreContainingIgnoreCase() {
        List<Product> listProductMatchNombre = productRepository.findByNombreContainingIgnoreCase("celular");
        assertThat(listProductMatchNombre.size()).isEqualTo(2);

        UUID idIphone = listProductMatchNombre.get(0).getId();
        UUID idXiaomi = listProductMatchNombre.get(1).getId();

        assertThat(idIphone).isEqualTo(productoGlobalIphone.getId());
        assertThat(idXiaomi).isEqualTo(productoGlobalXiaomi.getId());

    }

    @Test
    @DisplayName("[findInStock] Buscar todos los producto que tenga stock (stock>0)")
    void test_findInStock() {
        List<Product> productosConStock =productRepository.findInStock();
        assertThat(productosConStock.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[test_findByPriceLessThanAndStockLessThan] Buscar todos los producto que tenga stock (stock>0)")
    void findByPriceLessThanAndStockLessThan() {
        Float PRICE_MAX = 1000000f;
        Integer STOCK_MAX = 20;
        List<Product> productoNoSuperaStockYPrecioDeterminado = productRepository.findByPriceLessThanAndStockLessThan(PRICE_MAX, STOCK_MAX);

        assertThat(productoNoSuperaStockYPrecioDeterminado.size()).isEqualTo(1);
        assertThat(productoNoSuperaStockYPrecioDeterminado.get(0).getId()).isEqualTo(productoGlobalXiaomi.getId());
    }

    @Test
    @DisplayName("[save] dado un producto a guardar, se debe registrar en la base de dato")
    void testSave() {

        Product newProduct = Product.builder()
                .nombre("Lapto Dell 32Gb ram")
                .price(4500000f)
                .stock(4)
                .build();

        Product productoGuardado = productRepository.save(newProduct);

        assertThat(productoGuardado.getId()).isNotNull();
        assertThat(productoGuardado.getNombre()).isEqualTo("Lapto Dell 32Gb ram");
        assertThat(productoGuardado.getPrice()).isEqualTo(4500000f);
        assertThat(productoGuardado.getStock()).isEqualTo(4);
    }

    @Test
    @DisplayName("[Delete] Dado un producto existente cuando se elimine, no debe permanecer en la base de datos")
    void testDelete() {
        UUID id_bolso = productoGlobalBolso.getId();
        productRepository.deleteById(id_bolso);

        Optional<Product> productoSupuestamenteEliminado = productRepository.findById(id_bolso);
        assertThat(productoSupuestamenteEliminado).isEmpty();
    }

    @Test
    @DisplayName("[Read] se debe poner obtener todos los producto de la base de datos")
    void testRead() {
        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("[update] dado un producto existente cuando se actualiza debe guardar los cambio en la base de datos")
    void testUpdate() {

        UUID id_bolso = productoGlobalBolso.getId();
        Optional<Product> existingProduct = productRepository.findById(id_bolso);
        assertThat(existingProduct).isPresent();

        Product productoActualizado = existingProduct.get();
        productoActualizado.setStock(100);

        productRepository.save(productoActualizado);

        Optional<Product> productoConsultado = productRepository.findById(id_bolso);
        assertThat(productoConsultado).isPresent();
        assertThat(productoConsultado.get().getStock()).isEqualTo(100);
    }

}