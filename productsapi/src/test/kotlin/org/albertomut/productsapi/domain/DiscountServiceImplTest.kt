package org.albertomut.productsapi.domain

import org.albertomut.productsapi.domain.models.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class DiscountServiceImplTest {

    private val discountService = DiscountServiceImpl()

    @Test
    fun `calculateDiscount should apply the appropriate discount for Electronics category`() {
        val product = Product.builder()
            .sku("SKU0001")
            .price(BigDecimal("100.00"))
            .description("Product 1")
            .category("Electronics")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.15"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for Home & Kitchen category`() {
        val product = Product.builder()
            .sku("SKU0002")
            .price(BigDecimal("100.00"))
            .description("Product 2")
            .category("Home & Kitchen")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.25"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for product with SKU ending in 5`() {
        val product = Product.builder()
            .sku("SKU0005")
            .price(BigDecimal("100.00"))
            .description("Product 3")
            .category("Clothing")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for Electronics with SKU ending in 5`() {
        val product = Product.builder()
            .sku("SKU0005")
            .price(BigDecimal("100.00"))
            .description("Product 3")
            .category("Electronics")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply the appropriate discount for Home & Kitchen with SKU ending in 5`() {
        val product = Product.builder()
            .sku("SKU0005")
            .price(BigDecimal("100.00"))
            .description("Product 3")
            .category("Home & Kitchen")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.30"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply no discount for other categories`() {
        val product = Product.builder()
            .sku("SKU0003")
            .price(BigDecimal("100.00"))
            .description("Product 4")
            .category("Clothing")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.00"), discount.percentage)
    }

    @Test
    fun `calculateDiscount should apply no discount for other categories without sku ending in 5`() {
        val product = Product.builder()
            .sku("SKU0004")
            .price(BigDecimal("100.00"))
            .description("Product 4")
            .category("Clothing")
            .createdAt(LocalDateTime.now())
            .build()
        val discount = discountService.calculateDiscount(product)
        assertEquals(BigDecimal("0.00"), discount.percentage)
    }
}