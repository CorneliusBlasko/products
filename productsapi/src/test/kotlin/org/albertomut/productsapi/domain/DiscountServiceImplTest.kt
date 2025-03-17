package org.albertomut.productsapi.domain

import org.albertomut.productsapi.domain.models.Product
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class DiscountServiceImplTest {

    private val discountService = DiscountServiceImpl()

    @Test
    fun `calculateDiscount should apply discount for Electronics category`() {
        val product = Product("SKU0001", BigDecimal("100.00"), "Product 1", "Electronics", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.15"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply discount for Home & Kitchen category`() {
        val product = Product("SKU0002", BigDecimal("100.00"), "Product 2", "Home & Kitchen", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.25"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply discount for product with SKU ending in 5`() {
        val product = Product("SKU0005", BigDecimal("100.00"), "Product 3", "Clothing", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for Electronics with SKU ending in 5`() {
        val product = Product("SKU0005", BigDecimal("100.00"), "Product 3", "Electronics", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for Home & Kitchen with SKU ending in 5`() {
        val product = Product("SKU0005", BigDecimal("100.00"), "Product 3", "Home & Kitchen", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply no discount for other categories`() {
        val product = Product("SKU0003", BigDecimal("100.00"), "Product 4", "Clothing", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.00"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply no discount for other categories without sku ending in 5`() {
        val product = Product("SKU0004", BigDecimal("100.00"), "Product 4", "Clothing", LocalDateTime.now())
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.00"), discount.percentage)
    }
}