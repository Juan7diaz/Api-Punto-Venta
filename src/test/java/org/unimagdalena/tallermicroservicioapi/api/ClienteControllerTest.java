package org.unimagdalena.tallermicroservicioapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;
import org.unimagdalena.tallermicroservicioapi.services.cliente.ClienteServices;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ClienteServices clienteServices;
    @Autowired
    private ObjectMapper objectMapper;
    private Cliente cliente;
    private ClienteToShowDto clienteToShowDto;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllUsuarios_thenReturnListUsuarios() throws Exception {
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        List<ClienteToShowDto> clientes = List.of(cliente1,new ClienteToShowDto(
                UUID.randomUUID(),
                "Gian Manzanares",
                "gianManzanares@test.com",
                "Calle 40 #12-12"));
        when(clienteServices.findAllCliente()).thenReturn(clientes);

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(cliente1.id().toString())));
    }
    @Test
    void whenGetUserById_thenReturnsUsuario() throws Exception{
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        when(clienteServices.findClienteById(cliente1.id())).thenReturn(cliente1);

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(cliente1.id().toString())));
    }
}