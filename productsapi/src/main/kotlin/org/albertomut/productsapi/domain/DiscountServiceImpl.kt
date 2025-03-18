package org.albertomut.productsapi.domain

import org.albertomut.productsapi.domain.models.Discount
import org.albertomut.productsapi.domain.models.Product
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DiscountServiceImpl : DiscountService {

    override fun calculateDiscount(product: Product): Discount {
        return Discount.builder()
            .percentage(applyDiscount(product))
            .build()
    }

    fun applyDiscount(product: Product): BigDecimal {

        if (product.sku.endsWith("5")) {
            return BigDecimal("0.30")
        }

        if (product.category.equals("Home & Kitchen", ignoreCase = true)) {
            return BigDecimal("0.25")
        }

        if (product.category.equals("Electronics", ignoreCase = true)) {
            return BigDecimal("0.15")
        }

        return BigDecimal("0.00")
    }
}