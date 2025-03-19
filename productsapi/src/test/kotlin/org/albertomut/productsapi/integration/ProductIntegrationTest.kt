package org.albertomut.productsapi.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@AutoConfigureMockMvc
@Testcontainers
class ProductIntegrationTest(
    @Autowired val client: TestRestTemplate,
    @Autowired val jdbc: JdbcTemplate,
) {

    @Autowired
    private lateinit var mockMvc: MockMvc

    val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build()).registerModule(JavaTimeModule())

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
    fun `test check health endpoint`() {
        val entity = client.getForEntity<String>("/products/health")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("OK")
    }

    @Test
    fun `test products endpoint with parameters`() {
        val entity = client.getForEntity<String>("/products?sort=price&page=1")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("SKU0028")
        assertThat(entity.body).doesNotContain("SKU0001")
    }

    @Test
    internal fun `return default product catalog`() {
        mockMvc.perform(get("/products"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val responseContent = mockMvc.perform(get("/products")).andReturn().response.contentAsString

        val productPage: ProductPage = mapper.readValue(responseContent)

        assertEquals(30, productPage.totalElements)
        assertEquals(10, productPage.content.size)
        assertEquals("SKU0001", productPage.content[0].sku)
        assertEquals("Wireless Mouse with ergonomic design", productPage.content[0].description)
        assertEquals("Electronics", productPage.content[0].category)
    }

    @Test
    fun `getProducts with pagination and sorting should return correct page`() {
        mockMvc.perform(get("/products?page=1&size=5&sort=price&descending=true")).andExpect(MockMvcResultMatchers.status().isOk)

        val responseContent = mockMvc.perform(get("/products?page=1&size=5&sort=price&descending=true")).andReturn().response.contentAsString

        val productPage: ProductPage = mapper.readValue(responseContent)

        assertEquals(30, productPage.totalElements)
        assertEquals(5, productPage.content.size)
        assertEquals(1, productPage.number)
        assertEquals(5, productPage.size)
        assertEquals("SKU0029", productPage.content[0].sku)
        assertEquals(93.50, productPage.content[0].price)
        assertEquals(6, productPage.totalPages)

    }
}