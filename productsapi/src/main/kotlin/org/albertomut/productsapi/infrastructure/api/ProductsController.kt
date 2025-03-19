package org.albertomut.productsapi.infrastructure.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Products", description = "Product catalog operations")
class ProductsController(
    private val productService: ProductService
) {

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Checks if the controller is alive.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getHealth(): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @GetMapping
    @Operation(summary = "Get catalog", description = "Retrieves a paginated list of products ordered by SKU if there's no sorting or filtering criteria. " +
            "If the sorting criteria doesn't match any of the keywords (sku, price, description or category) an error is returned.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    fun getProducts(
        @Parameter(description = "Category to filter by")
        @RequestParam category: String?,
        @Parameter(description = "Sorting criteria")
        @RequestParam sort: String?,
        @Parameter(description = "Page number (0-based)")
        @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "Page size")
        @RequestParam(defaultValue = "10") size: Int,
        @Parameter(description = "Sorting order")
        @RequestParam(defaultValue = "false") descending: Boolean
    ): ResponseEntity<Page<Product>> {
        val validSortFields = listOf("sku", "price", "description", "category")
        if (sort != null && !validSortFields.contains(sort.lowercase())) {
            return ResponseEntity.badRequest().body(Page.empty())
        }
        val products = productService.getProducts(category, sort, descending, page, size)
        return ResponseEntity.ok(products)
    }
}