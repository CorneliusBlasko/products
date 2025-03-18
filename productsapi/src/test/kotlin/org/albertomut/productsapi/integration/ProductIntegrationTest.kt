package org.albertomut.productsapi.integration

import org.albertomut.productsapi.domain.models.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@Testcontainers
class ProductIntegrationTest(
    @Autowired val client: TestRestTemplate,
    @Autowired val jdbc: JdbcTemplate,
) {

    companion object {
        @Container
        val container = postgres("13-alpine") {
            withDatabaseName("productsdb")
            withUsername("postgres")
            withPassword("postgres")
            withInitScript("testdb.sql")
        }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
        }
    }

    @Test
    fun `test health endpoint`() {
        val entity = client.getForEntity<String>("/products/health")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("OK")
    }

    @Test
    fun `test products endpoint`() {
        val entity = client.getForEntity<String>("/products/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("SKU0001")
    }

    @Test
    fun `test products endpoint with parameters`() {
        val entity = client.getForEntity<String>("/products?sort=price&page=1")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("SKU0028")
        assertThat(entity.body).doesNotContain("SKU0001")
    }
}