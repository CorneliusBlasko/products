package org.albertomut.productsapi.infrastructure.api

import org.albertomut.productsapi.application.ProductService
import org.albertomut.productsapi.domain.models.Product
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductsController(
    private val productService: ProductService
) {

    @GetMapping("/health")
    fun getHealth(): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @GetMapping
    fun getProducts(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) sort: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "false") descending: Boolean
    ): ResponseEntity<Page<Product>> {
        val products = productService.getProducts(category, sort, descending, page, size)
        return ResponseEntity.ok(products)
    }
}