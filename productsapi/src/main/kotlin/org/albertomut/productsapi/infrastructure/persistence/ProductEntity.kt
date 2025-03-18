package org.albertomut.productsapi.infrastructure.persistence


import org.albertomut.productsapi.domain.models.Product
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
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

    companion object {
        fun fromDomain(product: Product): ProductEntity {
            return ProductEntity(
                product.sku,
                product.price,
                product.description,
                product.category,
                product.createdAt
            )
        }
    }
}