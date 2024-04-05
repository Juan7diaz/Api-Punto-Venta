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
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.pago.PagoToShowDto;
import org.unimagdalena.tallermicroservicioapi.dto.pedido.PedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.pago.PagoServices;
import org.unimagdalena.tallermicroservicioapi.utils.EstadoPedido;
import org.unimagdalena.tallermicroservicioapi.utils.MetodoPago;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PagoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PagoServices pagoServices;
    @Autowired
    private ObjectMapper objectMapper;
    PagoToShowDto pago;
    PedidoToShowDto pedido;
    @BeforeEach
    void setUp() {
        pedido = new PedidoToShowDto(
                UUID.randomUUID(),
                LocalDateTime.of(2024,3,30,10,0),
                EstadoPedido.ENVIADO,
                new ClienteToShowDto(
                        UUID.randomUUID(),
                        "Jachan Bananaras",
                        "jachanBananeras@test.com",
                        "Manzana 1 Casa 9 Las Bananeras"));
        pago = new PagoToShowDto(
                UUID.randomUUID(),
                20000,
                LocalDateTime.of(2024,4,1,8,0),
                MetodoPago.NEQUI,
                pedido);
    }
    @Test
    void whenGetAllPagos_thenReturnListPagos() throws Exception{
        List<PagoToShowDto> pagos = List.of(pago, new PagoToShowDto(
                UUID.randomUUID(),
                10500,
                LocalDateTime.of(2024,3,15,12,30),
                MetodoPago.DAVIPLATA,
                new PedidoToShowDto(
                        UUID.randomUUID(),
                        LocalDateTime.of(2024,3,10,10,0),
                        EstadoPedido.ENVIADO,
                        new ClienteToShowDto(
                                UUID.randomUUID(),
                                "Jachan Bananaras",
                                "jachanBananeras@test.com",
                                "Manzana 1 Casa 9 Las Bananeras"))));
        when(pagoServices.findAllPago()).thenReturn(pagos);
        mockMvc.perform(get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(pago.id().toString())));
    }
    @Test
    void whenGetPagoById_thenReturnsPago() throws Exception {
        when(pagoServices.findPagoById(pago.id())).thenReturn(pago);
        mockMvc.perform(get("/api/v1/payments/{id}", pago.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(pago.id().toString())));
    }
    @Test
    void whenGetPagoByPedidoIdAndMetodoPago_thenReturnsPago() throws Exception{
        when(pagoServices.findPagoByPedidoIdAndMetodoPago(pedido.id(),pago.metodoPago())).thenReturn(pago);
        mockMvc.perform(get("/api/v1/payments/order/{orderId}", pedido.id())
                        .param("metodopago",pago.metodoPago().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(pago.id().toString())));
    }
    @Test
    void whenGetPagoByDateRange_thenReturnsPago() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 25, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 4, 3, 17, 0);
        when(pagoServices.findPagoByFechaPagoBetween(startDate, endDate)).thenReturn(List.of(pago));
        mockMvc.perform(get("/api/v1/payments/date-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(pago.id().toString())));
    }
    @Test
    void whenPostPago_thenReturnsPagoSaved() throws Exception {
        PagoToSaveDto pagoToSave = new PagoToSaveDto(
                pago.totalPago(),
                pago.fechaPago(),
                pago.metodoPago(),
                new PedidoToShowDto(
                        pedido.id(),
                        null,
                        null,
                        null));
        when(pagoServices.savePago(pagoToSave)).thenReturn(pago);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(pago.id().toString())))
                .andExpect(jsonPath("$.totalPago", is(pago.totalPago())));
    }
    @Test
    void whenPutPagoById_thenReturnsPagoUpdated() throws Exception {
        PagoToSaveDto toUpdate = new PagoToSaveDto(
                null,
                null,
                MetodoPago.PAYPAL,
                null);
        PagoToShowDto pagoUpdated = new PagoToShowDto(
                pago.id(),
                pago.totalPago(),
                pago.fechaPago(),
                toUpdate.metodoPago(),
                pago.pedido());
        when(pagoServices.updatePagoById(pagoUpdated.id(), toUpdate)).thenReturn(pagoUpdated);

        mockMvc.perform(put("/api/v1/payments/{id}", pagoUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(pagoUpdated.id().toString())))
                .andExpect(jsonPath("$.metodoPago", is(pagoUpdated.metodoPago().toString())));
    }
    @Test
    void whenDeletePagoById_thenReturnsMessage() throws Exception {
        doNothing().when(pagoServices).deletePagoById(pago.id());
        mockMvc.perform(delete("/api/v1/payments/{id}", pago.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pago eliminado"));
    }
}