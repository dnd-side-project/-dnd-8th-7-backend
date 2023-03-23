package com.dnd8th;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
public class TestContainerConfig {

    @Container
    static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer()
            .withDatabaseName("testDB")
            .withInitScript("db/init.sql");
}
