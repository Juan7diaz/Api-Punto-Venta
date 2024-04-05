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
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.pedido.PedidoServices;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PedidoServices pedidoServices;
    @Autowired
    private ObjectMapper objectMapper;
    PedidoToShowDto pedido;
    ClienteToShowDto cliente;
    @BeforeEach
    void setUp() {
        cliente = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        pedido = new PedidoToShowDto(
                UUID.randomUUID(),
                LocalDateTime.of(2024,3,30,10,0),
                EstadoPedido.PENDIENTE,
                cliente);
    }

    @Test
    void whenGetAllPedidos_thenReturnListPedidos() throws Exception{
        List<PedidoToShowDto> pedidos = List.of(pedido,new PedidoToShowDto(
                UUID.randomUUID(),
                LocalDateTime.of(2024,3,15,12,30),
                EstadoPedido.ENVIADO,
                cliente));
        when(pedidoServices.findAllPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(pedido.id().toString())));
    }
    @Test
    void whenGetPedidoById_thenReturnsPedido() throws Exception{
        when(pedidoServices.findPedidoById(pedido.id())).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/orders/{id}",pedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(pedido.id().toString())));
    }
    @Test
    void whenGetPedidoByClienteIdAndStatus_thenReturnsPedido() throws Exception{
        when(pedidoServices.findPedidosByClienteIdAndStatus(cliente.id(),pedido.status())).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/orders/customer/{customerId}",cliente.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status",pedido.status().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(pedido.id().toString())));
    }
    @Test
    void whenGetPedidoByDateRange_thenReturnsPedido() throws Exception{
        LocalDateTime startDate = LocalDateTime.of(2024,3,25,10,0);
        LocalDateTime endDate = LocalDateTime.of(2024,4,1,17,0);
        when(pedidoServices.findPedidosByFechaPedidoBetween(startDate,endDate)).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/orders/date-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate",startDate.toString())
                        .param("endDate",endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(pedido.id().toString())));
    }
    @Test
    void whenPostPedido_thenReturnsPedidoSaved() throws Exception{
        PedidoToSaveDto pedidoToSave = new PedidoToSaveDto(
                pedido.fechaPedido(),
                pedido.status(),
                new ClienteToShowDto(
                        cliente.id(),
                        null,
                        null,
                        null));

        when(pedidoServices.savePedido(pedidoToSave)).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(pedido.id().toString())))
                .andExpect(jsonPath("$.status",is(pedido.status().toString())));
    }
    @Test
    void whenPutPedidoById_thenReturnsPedidoUpdated() throws Exception{
        PedidoToSaveDto toUpdate = new PedidoToSaveDto(
                null,
                EstadoPedido.ENTREGADO,
                null);
        PedidoToShowDto pedidoUpdated = new PedidoToShowDto(
                pedido.id(),
                pedido.fechaPedido(),
                toUpdate.status(),
                pedido.cliente());

        when(pedidoServices.updatePedidoById(pedidoUpdated.id(),toUpdate)).thenReturn(pedidoUpdated);

        mockMvc.perform(put("/api/v1/orders/{id}",pedidoUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(pedidoUpdated.id().toString())))
                .andExpect(jsonPath("$.status",is(pedidoUpdated.status().toString())));
    }
    @Test
    void whenDeletePedidoById_thenReturnsMessage() throws Exception{
        doNothing().when(pedidoServices).deletePedidoById(pedido.id());

        mockMvc.perform(delete("/api/v1/orders/{id}",pedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido Eliminado"));
    }
}