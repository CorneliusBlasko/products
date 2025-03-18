package org.albertomut.productsapi.domain.models

import java.math.BigDecimal

class Discount private constructor(val percentage: BigDecimal) {

    data class Builder(var percentage: BigDecimal? = null) {
        fun percentage(percentage: BigDecimal) = apply { this.percentage = percentage }

        fun build(): Discount {
            val percentage = percentage ?: throw IllegalStateException("Percentage is required")
            return Discount(percentage)
        }
    }

    companion object {
        fun builder(): Builder = Builder()
    }
}