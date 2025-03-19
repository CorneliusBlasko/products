package org.albertomut.productsapi.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.albertomut.productsapi.domain.models.Product
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    val sku: String,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val createdAt: LocalDateTime
) {
    fun toDomain(): Product {
        return Product.builder()
            .sku(sku)
            .price(price)
            .description(description)
            .category(category)
            .createdAt(createdAt)
            .build()
    }
}