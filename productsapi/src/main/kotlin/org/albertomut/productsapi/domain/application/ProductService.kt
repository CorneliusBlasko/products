package org.albertomut.productsapi.domain.application

import org.albertomut.productsapi.domain.models.Product
import org.springframework.data.domain.Page

interface ProductService {
    fun getProducts(category: String?, sortingTerm: String?, descending: Boolean, page: Int, size: Int): Page<Product>
}