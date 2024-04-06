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
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.itemPedido.ItemPedidoServices;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ItemPedidoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ItemPedidoServices itemPedidoServices;
    @Autowired
    private ObjectMapper objectMapper;
    PedidoToShowDto pedido;
    ProductToShowDto product;
    ItemPedidoToShowDto itemPedido;
    @BeforeEach
    void setUp() {
        pedido = new PedidoToShowDto(
                UUID.randomUUID(),
                LocalDateTime.of(2024, 3, 30, 10, 0),
                EstadoPedido.ENVIADO,
                new ClienteToShowDto(
                        UUID.randomUUID(),
                        "Jachan Bananaras",
                        "jachanBananeras@test.com",
                        "Manzana 1 Casa 9 Las Bananeras"));
        product = new ProductToShowDto(
                UUID.randomUUID(),
                "Computador Acer",
                (float)2354,
                21);
        itemPedido = new ItemPedidoToShowDto(
                UUID.randomUUID(),
                10,
                (float)2354,
                pedido,
                product);
    }
    @Test
    void whenGetAllItemsPedido_thenReturnListItemsPedido() throws Exception {
        List<ItemPedidoToShowDto> itemsPedido = List.of(itemPedido, new ItemPedidoToShowDto(
                UUID.randomUUID(),
                15,
                (float) 2354,
                new PedidoToShowDto(
                        UUID.randomUUID(),
                        LocalDateTime.of(2024, 3, 10, 10, 0),
                        EstadoPedido.ENVIADO,
                        new ClienteToShowDto(
                                UUID.randomUUID(),
                                "Jachan Bananaras",
                                "jachanBananeras@test.com",
                                "Manzana 1 Casa 9 Las Bananeras")),
                product));
        when(itemPedidoServices.getAllItemPedido()).thenReturn(itemsPedido);
        mockMvc.perform(get("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(itemPedido.id().toString())));
    }
    @Test
    void whenGetItemPedidoById_thenReturnsItemPedido() throws Exception {
        when(itemPedidoServices.getItemPedidoById(itemPedido.id())).thenReturn(itemPedido);
        mockMvc.perform(get("/api/v1/order-items/{id}", itemPedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(itemPedido.id().toString())));
    }
    @Test
    void whenGetItemPedidoByPedidoId_thenReturnsItemPedido() throws Exception{
        when(itemPedidoServices.findItemPedidoToShowDtoByPedidoId(pedido.id())).thenReturn(List.of(itemPedido));
        mockMvc.perform(get("/api/v1/order-items/order/{orderId}", pedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(itemPedido.id().toString())));
    }
    @Test
    void whenGetItemPedidoByProductId_thenReturnsItemPedido() throws Exception{
        when(itemPedidoServices.findItemPedidoToShowDtoByProductId(product.id())).thenReturn(List.of(itemPedido));
        mockMvc.perform(get("/api/v1/order-items/product/{orderId}", product.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(itemPedido.id().toString())));
    }
    @Test
    void whenPostItemPedido_thenReturnsItemPedidoSaved() throws Exception {
        ItemPedidoToSaveDto itemPedidoToSave = new ItemPedidoToSaveDto(
                itemPedido.cantidad(),
                itemPedido.precioUnitario(),
                new PedidoToShowDto(
                        pedido.id(),
                        null,
                        null,
                        null),
                new ProductToShowDto(
                        product.id(),
                        null,
                        null,
                        null));
        when(itemPedidoServices.saveItemPedido(itemPedidoToSave)).thenReturn(itemPedido);
        mockMvc.perform(post("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(itemPedido.id().toString())))
                .andExpect(jsonPath("$.cantidad", is(itemPedido.cantidad())));
    }
    @Test
    void whenPutItemPedidoById_thenReturnsItemPedidoUpdated() throws Exception {
        ItemPedidoToSaveDto toUpdate = new ItemPedidoToSaveDto(
                8,
                null,
                null,
                null);
        ItemPedidoToShowDto itemPedidoUpdated = new ItemPedidoToShowDto(
                itemPedido.id(),
                toUpdate.cantidad(),
                itemPedido.precioUnitario(),
                itemPedido.pedido(),
                itemPedido.product());
        when(itemPedidoServices.updateItemPedido(itemPedidoUpdated.id(), toUpdate)).thenReturn(itemPedidoUpdated);
        mockMvc.perform(put("/api/v1/order-items/{id}", itemPedidoUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(itemPedidoUpdated.id().toString())))
                .andExpect(jsonPath("$.cantidad", is(itemPedidoUpdated.cantidad())));
    }
    @Test
    void whenDeleteItemPedidoById_thenReturnsMessage() throws Exception {
        doNothing().when(itemPedidoServices).deleteItemPedidoById(itemPedido.id());
        mockMvc.perform(delete("/api/v1/order-items/{id}", itemPedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("ItemPedido con ID "+itemPedido.id()+" eliminado"));
    }

}