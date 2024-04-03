package org.unimagdalena.tallermicroservicioapi;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractIntegrationDBTest{

// Esta es la configuración que me sirvió para que el contenedor de PostgreSQL se levantara
//    @ServiceConnection
//    PostgreSQLContainer<?> postgreSQLContainer(){
//        return new PostgreSQLContainer<>("postgres:15-alpine");
//    }


    // Esta es la configuración explicada en clase
    // si no funciona, descomentar la configuración anterior y comentar esta
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

}