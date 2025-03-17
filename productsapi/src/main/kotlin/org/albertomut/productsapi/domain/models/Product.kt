package org.albertomut.productsapi.domain.models

import java.math.BigDecimal
import java.time.LocalDateTime

data class Product(
    val sku: String,
    var price: BigDecimal,
    val description: String,
    val category: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)