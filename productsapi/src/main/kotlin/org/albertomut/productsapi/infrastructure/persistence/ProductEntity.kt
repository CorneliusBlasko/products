package org.albertomut.productsapi.infrastructure.persistence

import org.albertomut.productsapi.domain.models.Product
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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