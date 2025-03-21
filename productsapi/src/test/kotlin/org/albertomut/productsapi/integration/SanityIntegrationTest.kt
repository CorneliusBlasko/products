package org.albertomut.productsapi.integration

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class SanityIntegrationTest {

    companion object {
        @Container
        val container = postgres("13-alpine") {
            withDatabaseName("db")
            withUsername("user")
            withPassword("password")
            withInitScript("testdb.sql")
        }
    }

    @Test
    fun `container is up and running`() {
        Assertions.assertTrue(container.isRunning)
    }
}