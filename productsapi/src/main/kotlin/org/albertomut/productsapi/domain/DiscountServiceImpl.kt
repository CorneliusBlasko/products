package org.albertomut.productsapi.domain

import org.albertomut.productsapi.domain.models.Discount
import org.albertomut.productsapi.domain.models.Product
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DiscountServiceImpl : DiscountService {

    override fun calculateDiscount(product: Product): Discount {
        var discountPercentage = BigDecimal("0.00")

        if (product.sku.endsWith("5")) {
            discountPercentage = BigDecimal("0.30")
            return Discount(discountPercentage)
        }

        if (product.category.equals("Home & Kitchen", ignoreCase = true)) {
            discountPercentage = BigDecimal("0.25")
            return Discount(discountPercentage)
        }

        if (product.category.equals("Electronics", ignoreCase = true)) {
            discountPercentage = BigDecimal("0.15")
            return Discount(discountPercentage)
        }

        return Discount(discountPercentage)
    }
}