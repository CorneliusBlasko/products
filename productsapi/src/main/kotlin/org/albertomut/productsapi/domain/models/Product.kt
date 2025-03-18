package org.albertomut.productsapi.domain.models

import java.math.BigDecimal
import java.time.LocalDateTime

class Product private constructor(
    val sku: String,
    var price: BigDecimal,
    val description: String,
    val category: String,
    val createdAt: LocalDateTime
) {

    data class Builder(
        var sku: String? = null,
        var price: BigDecimal? = null,
        var description: String? = null,
        var category: String? = null,
        var createdAt: LocalDateTime? = null
    ) {
        fun sku(sku: String) = apply { this.sku = sku }
        fun price(price: BigDecimal) = apply { this.price = price }
        fun description(description: String) = apply { this.description = description }
        fun category(category: String) = apply { this.category = category }
        fun createdAt(createdAt: LocalDateTime) = apply { this.createdAt = createdAt }

        fun build(): Product {
            val sku = sku ?: throw IllegalStateException("SKU is required")
            val price = price ?: throw IllegalStateException("Price is required")
            val description = description ?: throw IllegalStateException("Description is required")
            val category = category ?: throw IllegalStateException("Category is required")
            val createdAt = createdAt ?: LocalDateTime.now()

            return Product(sku, price, description, category, createdAt)
        }
    }

    companion object {
        fun builder(): Builder = Builder()
    }
}