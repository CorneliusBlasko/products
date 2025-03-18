package org.albertomut.productsapi.application

import org.albertomut.productsapi.domain.DiscountService
import org.albertomut.productsapi.domain.DiscountServiceImpl
import org.albertomut.productsapi.domain.application.ProductService
import org.albertomut.productsapi.domain.application.ProductServiceImpl
import org.albertomut.productsapi.domain.models.Discount
import org.albertomut.productsapi.domain.models.Product
import org.albertomut.productsapi.infrastructure.persistence.ProductEntity
import org.albertomut.productsapi.infrastructure.persistence.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {

    private lateinit var discountService: DiscountService

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var productService: ProductServiceImpl

    private lateinit var productEntity1: ProductEntity
    private lateinit var productEntity2: ProductEntity
    private lateinit var product1: Product
    private lateinit var product2: Product

    @BeforeEach
    fun setUp() {
        //Discounted price should be 16.99
        productEntity1 = ProductEntity("SKU0001", BigDecimal("19.99"), "Wireless Mouse with ergonomic design", "Electronics", LocalDateTime.now())
        //Discounted price should be 22.12
        productEntity2 = ProductEntity("SKU0003", BigDecimal("29.50"), "A Stainless Steel Water Bottle, 1L", "Home & Kitchen", LocalDateTime.now())
        product1 = productEntity1.toDomain()
        product2 = productEntity2.toDomain()

        discountService = DiscountServiceImpl()
        productService = ProductServiceImpl(discountService, productRepository)
    }

    @Test
    fun `getProducts should return discounted products' correct price`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"sku"))
        val productEntities = listOf(productEntity1, productEntity2)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, "sku", true, 0, 10)

        assertEquals(2, result.content.size)
        assertEquals(BigDecimal("16.99"), result.content[0].price)
        assertEquals(BigDecimal("22.12"), result.content[1].price)
    }

    @Test
    fun `getProducts should filter by category`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"sku"))
        val productEntities = listOf(productEntity1)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findByCategoryIgnoreCase("Electronics", pageable)).thenReturn(page)

        val result = productService.getProducts("Electronics", "sku", true, 0, 10)

        assertEquals(1, result.content.size)
        assertEquals(("Electronics"), result.content[0].category)
    }

    @Test
    fun `getProducts should sort by price`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"price"))
        val productEntities = listOf(productEntity1, productEntity2)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, "price", true, 0, 10)

        assertEquals(2, result.content.size)
        assertEquals(BigDecimal("16.99"), result.content[0].price)
    }

    @Test
    fun `getProducts should sort by category`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"category"))
        val productEntities = listOf(productEntity1, productEntity2)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, "category", true, 0, 10)

        assertEquals("Electronics", result.content[0].category)
    }

    @Test
    fun `getProducts should sort by description`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"description"))
        val productEntities = listOf(productEntity2, productEntity1)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, "description", false, 0, 10)

        assertEquals(2, result.content.size)
        assertEquals("A Stainless Steel Water Bottle, 1L", result.content[0].description)
    }

    @Test
    fun `getProducts should sort by createdAt`() {
        val now = LocalDateTime.now()
        productEntity1 = ProductEntity("SKU0001", BigDecimal("19.99"), "Wireless Mouse with ergonomic design", "Electronics", now)
        //Discounted price should be 22.12
        productEntity2 = ProductEntity("SKU0003", BigDecimal("29.50"), "A Stainless Steel Water Bottle, 1L", "Home & Kitchen", now.plusSeconds(1L))
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"createdAt"))
        val productEntities = listOf(productEntity1, productEntity2)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, "createdAt", false, 0, 10)

        assertEquals(2, result.content.size)
        assertEquals(now, result.content[0].createdAt)
    }

    @Test
    fun `etProducts should sort by sku if no sort is provided`() {
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"sku"))
        val productEntities = listOf(productEntity1, productEntity2)
        val page: Page<ProductEntity> = PageImpl(productEntities, pageable, productEntities.size.toLong())

        `when`(productRepository.findAll(pageable)).thenReturn(page)

        val result = productService.getProducts(null, null, true, 0, 10)

        assertEquals(2, result.content.size)
        assertEquals("SKU0001", result.content[0].sku)
    }

}