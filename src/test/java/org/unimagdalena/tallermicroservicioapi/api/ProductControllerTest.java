package org.unimagdalena.tallermicroservicioapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.product.ProductServices;

import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductServices productServices;
    @Autowired
    private ObjectMapper objectMapper;
    ProductToShowDto product;
    @BeforeEach
    void setUp() {
        product = new ProductToShowDto(
                UUID.randomUUID(),
                "Computador Intel",
                (float)52364,
                10);
    }
    @Test
    void whenGetAllProducts_thenReturnsListProducts() throws Exception{
        List<ProductToShowDto> productos = List.of(product, new ProductToShowDto(
                UUID.randomUUID(),
                "Computador Acer",
                (float)32865,
                15));
        when(productServices.findAllProducts()).thenReturn(productos);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(product.id().toString())));
    }
    @Test
    void whenGetProductById_thenReturnsProduct() throws Exception{
        when(productServices.findProductById(product.id())).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", product.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(product.id().toString())));
    }
    @Test
    void whenGetProductByInStock_thenReturnsProduct() throws Exception{
        when(productServices.findProductInStock()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/v1/products/instock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(product.id().toString())));
    }
    @Test
    void whenGetProductBySearchTerm_thenReturnsProduct() throws Exception{
        String searchTerm = "computador";
        when(productServices.findProductByNombre(searchTerm)).thenReturn(List.of(product));

        mockMvc.perform(get("/api/v1/products/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("searchTerm",searchTerm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(product.id().toString())));
    }
    @Test
    void whenPostProduct_thenReturnsProductSaved() throws Exception{
        ProductToSaveDto productToSave = new ProductToSaveDto(
                product.nombre(),
                product.price(),
                product.stock());
        when(productServices.saveProduct(productToSave)).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(product.id().toString())))
                .andExpect(jsonPath("$.nombre", is(product.nombre())));

    }
    @Test
    void whenPutProductById_thenReturnsProductUpdated() throws Exception{
        ProductToSaveDto toUpdate = new ProductToSaveDto(
                null,
                null,
                20);
        ProductToShowDto productUpdated = new ProductToShowDto(
                product.id(),
                product.nombre(),
                product.price(),
                toUpdate.stock());

        when(productServices.updateProductById(productUpdated.id(), toUpdate)).thenReturn(productUpdated);

        mockMvc.perform(put("/api/v1/products/{id}", productUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(productUpdated.id().toString())))
                .andExpect(jsonPath("$.stock", is(productUpdated.stock())));
    }
    @Test
    void whenDeleteProductById_thenReturnsMessage() throws Exception{
        doNothing().when(productServices).deleteProductById(product.id());

        mockMvc.perform(delete("/api/v1/products/{id}", product.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto Eliminado"));
    }

}