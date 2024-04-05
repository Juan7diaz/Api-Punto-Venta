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
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.detalleEnvio.DetalleEnvioToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.detalleEnvio.DetalleEnvioServices;
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
class DetalleEnvioControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private DetalleEnvioServices detalleEnvioServices;
    @Autowired
    private ObjectMapper objectMapper;
    DetalleEnvioToShowDto detalleEnvio;
    PedidoToShowDto pedido;
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
        detalleEnvio = new DetalleEnvioToShowDto(
                UUID.randomUUID(),
                "Calle 56 #90-02 Las Bananeras",
                "COORDINADORA",
                15354,
                pedido);
    }
    @Test
    void whenGetAllDetallesEnvio_thenReturnListDetallesEnvio() throws Exception {
        List<DetalleEnvioToShowDto> detallesEnvio = List.of(detalleEnvio, new DetalleEnvioToShowDto(
                UUID.randomUUID(),
                "Calle 56 #90-02 Las Bananeras",
                "INTERRAPIDISIMOS",
                59741,
                new PedidoToShowDto(
                        UUID.randomUUID(),
                        LocalDateTime.of(2024, 3, 10, 10, 0),
                        EstadoPedido.ENVIADO,
                        new ClienteToShowDto(
                                UUID.randomUUID(),
                                "Jachan Bananaras",
                                "jachanBananeras@test.com",
                                "Manzana 1 Casa 9 Las Bananeras"))));
        when(detalleEnvioServices.getAllDetalleEnvio()).thenReturn(detallesEnvio);
        mockMvc.perform(get("/api/v1/shipping")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(detalleEnvio.id().toString())));
    }
    @Test
    void whenGetDetalleEnvioById_thenReturnsDetalleEnvio() throws Exception {
        when(detalleEnvioServices.getDetalleEnvioById(detalleEnvio.id())).thenReturn(detalleEnvio);
        mockMvc.perform(get("/api/v1/shipping/{id}", detalleEnvio.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(detalleEnvio.id().toString())));
    }
    @Test
    void whenGetDetalleEnvioByTransportadora_thenReturnsDetalleEnvio() throws Exception{
        when(detalleEnvioServices.getDetalleEnvioByTransportadora(detalleEnvio.transportadora())).thenReturn(List.of(detalleEnvio));
        mockMvc.perform(get("/api/v1/shipping/carrier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name",detalleEnvio.transportadora()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].transportadora", is(detalleEnvio.transportadora())));
    }
    @Test
    void whenGetDetalleEnvioByPedidoId_thenReturnsDetalleEnvio() throws Exception{
        when(detalleEnvioServices.getDetalleEnvioByPedidoId(pedido.id())).thenReturn(detalleEnvio);
        mockMvc.perform(get("/api/v1/shipping/order/{orderId}", pedido.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.pedido.id", is(pedido.id().toString())));
    }
    @Test
    void whenPostDetalleEnvio_thenReturnsDetalleEnvioSaved() throws Exception {
        DetalleEnvioToSaveDto detalleEnvioToSave = new DetalleEnvioToSaveDto(
                detalleEnvio.direccion(),
                detalleEnvio.transportadora(),
                detalleEnvio.numeroGuia(),
                new PedidoToShowDto(
                        pedido.id(),
                        null,
                        null,
                        null));
        when(detalleEnvioServices.saveDetalleEnvio(detalleEnvioToSave)).thenReturn(detalleEnvio);
        mockMvc.perform(post("/api/v1/shipping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(detalleEnvio.id().toString())))
                .andExpect(jsonPath("$.transportadora", is(detalleEnvio.transportadora())));
    }
    @Test
    void whenPutDetalleEnvioById_thenReturnsDetalleEnvioUpdated() throws Exception {
        DetalleEnvioToSaveDto toUpdate = new DetalleEnvioToSaveDto(
                null,
                null,
                32874,
                null);
        DetalleEnvioToShowDto detalleEnvioUpdated = new DetalleEnvioToShowDto(
                detalleEnvio.id(),
                detalleEnvio.direccion(),
                detalleEnvio.transportadora(),
                toUpdate.numeroGuia(),
                detalleEnvio.pedido());
        when(detalleEnvioServices.updateDetalleEnvioById(detalleEnvioUpdated.id(), toUpdate)).thenReturn(detalleEnvioUpdated);
        mockMvc.perform(put("/api/v1/shipping/{id}", detalleEnvioUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(detalleEnvioUpdated.id().toString())))
                .andExpect(jsonPath("$.numeroGuia", is(detalleEnvioUpdated.numeroGuia())));
    }
    @Test
    void whenDeleteDetalleEnvioById_thenReturnsMessage() throws Exception {
        doNothing().when(detalleEnvioServices).deleteDetalleEnvioById(detalleEnvio.id());
        mockMvc.perform(delete("/api/v1/shipping/{id}", detalleEnvio.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Detalle de envío eliminado"));
    }
}