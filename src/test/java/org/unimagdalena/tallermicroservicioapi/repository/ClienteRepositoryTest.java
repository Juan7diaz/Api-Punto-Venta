package org.unimagdalena.tallermicroservicioapi.repository;

import ch.qos.logback.core.net.server.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unimagdalena.tallermicroservicioapi.AbstractIntegrationDBTest;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteRepositoryTest extends  AbstractIntegrationDBTest{

    ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    private Cliente clienteglobalJuan;
    private Cliente clienteglobalGian;
    private Cliente clienteglobalDaniel;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        clienteglobalJuan = Cliente.builder()
                .nombre("Juan Luis Diaz Guerrero")
                .direccion("Rio frio zona bananera calle 2 carrera 404")
                .email("juandiazlg@unimagdalena.edu.co")
                .build();
        clienteglobalGian = Cliente.builder()
                .nombre("Gian Marcos Astori Payares")
                .direccion("Santa Marta via buena vista #44-24")
                .email("gianmarcomp@unimagdalena.edu.co")
                .build();
        clienteglobalDaniel = Cliente.builder()
                .nombre("Daniel Jose Cogollos Ceron")
                .direccion("Santa marta Calle 2 carrera 34")
                .email("danielcogollosjc@unimagdalena.edu.co")
                .build();
        clienteRepository.save(clienteglobalJuan);
        clienteRepository.save(clienteglobalGian);
        clienteRepository.save(clienteglobalDaniel);

    }

    @Test
    @DisplayName("[findByEmail] Dado un correo se debe buscar el cliente asociado a dicho correo")
    void test_findByEmail() {
        Optional<Cliente> clienteBuscadoPorEmail = clienteRepository.findByEmail("juandiazlg@unimagdalena.edu.co");
        assertThat(clienteBuscadoPorEmail).isPresent();

        UUID id_clienteBuscado = clienteBuscadoPorEmail.get().getId();
        assertThat(id_clienteBuscado).isEqualTo(clienteglobalJuan.getId());
    }

    @Test
    @DisplayName("[findByDireccionContainingIgnoreCase] Dado una direccion, se debe buscar todos los clientes que hagan match con dicha direccion")
    void test_findByDireccionContainingIgnoreCase(){
        List<Cliente> clienteMatchDireccion = clienteRepository.findByDireccionContainingIgnoreCase("santa marta");
        assertThat(clienteMatchDireccion.size()).isEqualTo(2);

        UUID id_clienteGian = clienteMatchDireccion.get(0).getId();
        UUID id_clienteDaniel = clienteMatchDireccion.get(1).getId();

        assertThat(id_clienteGian).isEqualTo(clienteglobalGian.getId());
        assertThat(id_clienteDaniel).isEqualTo(clienteglobalDaniel.getId());
    }

    @Test
    @DisplayName("[test_findByNombreStartingWithIgnoreCase] dado un nombre se debe buscar todos los clientes que inicie por dicho nombre")
    void test_findByNombreStartingWithIgnoreCase(){
        List<Cliente> clienteMatchNombre = clienteRepository.findByNombreStartingWithIgnoreCase("gian");
        assertThat(clienteMatchNombre.size()).isEqualTo(1);

        UUID id_clienteGian = clienteMatchNombre.get(0).getId();
        assertThat(id_clienteGian).isEqualTo(clienteglobalGian.getId());
    }

    @Test
    @DisplayName("[save] Dado un nuevo usuario, cuando se guarda, debe persistirse en la base de datos")
    void testSave() {
        Cliente clienteHassam = Cliente.builder()
                .nombre("Hassam Barranco")
                .email("hassambarranco@unimagdalena.edu.co")
                .direccion("Santa marta curinca calle 23i carrera 34j")
                .build();

        Cliente guardado = clienteRepository.save(clienteHassam);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    @DisplayName("[update] Dado un cliente existente, cuando se actualiza, se debe guardar los cambios en la base de datos")
    void testUpdate() {
        UUID idClienteGlobal = clienteglobalJuan.getId();
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(idClienteGlobal);

        assertThat(clienteEncontrado).isPresent();

        Cliente clienteActualizado = clienteEncontrado.get();
        clienteActualizado.setNombre("Luis Diaz");

        Cliente clienteGuardado = clienteRepository.save(clienteActualizado);
        assertThat(clienteGuardado.getNombre()).isEqualTo("Luis Diaz");
    }

    @Test
    @DisplayName("[delete] Dado un cliente existente, cuando se elimina, se debe eliminar de la base de datos")
    void testDelete() {
        UUID idClienteGlobalDaniel = clienteglobalDaniel.getId();
        clienteRepository.deleteById(idClienteGlobalDaniel);

        Optional<Cliente> clienteEncontrado = clienteRepository.findById(idClienteGlobalDaniel);
        assertThat(clienteEncontrado).isEmpty();
    }

    @Test
    @DisplayName("[read] se debe poder recuperar los clientes de la base de datos")
    void testRead(){
        List<Cliente> listaTodoCliente= clienteRepository.findAll();
        assertThat(listaTodoCliente.size()).isEqualTo(3);
    }




}