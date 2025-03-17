package org.albertomut.productsapi.domain

import org.albertomut.productsapi.domain.models.Discount
import org.albertomut.productsapi.domain.models.Product

interface DiscountService {
    fun calculateDiscount(product: Product): Discount
}