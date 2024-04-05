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
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.cliente.ClienteToShowDto;
import org.unimagdalena.tallermicroservicioapi.services.cliente.ClienteServices;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllClientes_thenReturnListClientes() throws Exception {
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
    void whenGetClienteById_thenReturnsCliente() throws Exception{
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        when(clienteServices.findClienteById(cliente1.id())).thenReturn(cliente1);

        mockMvc.perform(get("/api/v1/customers/{id}",cliente1.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(cliente1.id().toString())));
    }
    @Test
    void whenGetClienteByEmail_thenReturnsCliente() throws Exception{
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        when(clienteServices.findClienteByEmail(cliente1.email())).thenReturn(cliente1);

        mockMvc.perform(get("/api/v1/customers/email/{email}",cliente1.email())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.email",is(cliente1.email())));
    }
    @Test
    void whenGetClienteByCity_thenReturnsCliente() throws Exception{
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        ClienteToShowDto cliente2 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Yan Payaros",
                "yanPayaros@test.com",
                "Manzana 2 Casa 92 Las Bananeras");
        when(clienteServices.findClienteByDireccionContainingIgnoreCase("las bananeras")).thenReturn(List.of(cliente1,cliente2));

        mockMvc.perform(get("/api/v1/customers/city")
                        .param("cityName","las bananeras")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].direccion",is(cliente1.direccion())))
                .andExpect(jsonPath("$[1].direccion",is(cliente2.direccion())));
    }
    @Test
    void whenPostCliente_thenReturnsClienteSaved() throws Exception{
        ClienteToSaveDto clienteToSave = new ClienteToSaveDto(
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");
        ClienteToShowDto clienteSaved = new ClienteToShowDto(
                UUID.randomUUID(),
                clienteToSave.nombre(),
                clienteToSave.email(),
                clienteToSave.direccion());

        when(clienteServices.SaveCliente(clienteToSave)).thenReturn(clienteSaved);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(clienteSaved.id().toString())))
                .andExpect(jsonPath("$.nombre",is(clienteSaved.nombre())));
    }
    @Test
    void whenPutClienteById_thenReturnsClienteUpdated() throws Exception{
        ClienteToSaveDto toUpdate = new ClienteToSaveDto(
                null,
                "jachanBananeras@gmail.com",
                null);
        ClienteToShowDto clienteUpdated = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                toUpdate.email(),
                "Manzana 1 Casa 9 Las Bananeras");

        when(clienteServices.updateClienteById(clienteUpdated.id(),toUpdate)).thenReturn(clienteUpdated);

        mockMvc.perform(put("/api/v1/customers/{id}",clienteUpdated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id",is(clienteUpdated.id().toString())))
                .andExpect(jsonPath("$.email",is(clienteUpdated.email())));
    }
    @Test
    void whenDeleteClienteById_thenReturnsMessage() throws Exception{
        ClienteToShowDto cliente1 = new ClienteToShowDto(
                UUID.randomUUID(),
                "Jachan Bananaras",
                "jachanBananeras@test.com",
                "Manzana 1 Casa 9 Las Bananeras");

       doNothing().when(clienteServices).deleteClienteById(cliente1.id());

        mockMvc.perform(delete("/api/v1/customers/{id}",cliente1.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente eliminado"));
    }
}