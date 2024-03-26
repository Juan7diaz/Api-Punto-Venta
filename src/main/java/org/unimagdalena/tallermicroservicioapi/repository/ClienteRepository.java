package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unimagdalena.tallermicroservicioapi.entities.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    // Encontrar cliente(s) por email
    Optional<Cliente> findByEmail(String email);

    // Encontrar clientes por dirección
    Optional<Cliente> findByDireccionContainingIgnoreCase(String direccion);

    // Encontrar clientes por todos los clientes que comiencen por un nombre
    Optional<Cliente> findByNombreStartingWithIgnoreCase(String nombre);

}
