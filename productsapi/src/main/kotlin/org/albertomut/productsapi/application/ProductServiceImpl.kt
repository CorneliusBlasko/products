package org.albertomut.productsapi.application

import org.albertomut.productsapi.domain.DiscountService
import org.albertomut.productsapi.domain.models.Product
import org.albertomut.productsapi.infrastructure.persistence.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.RoundingMode

@Service
class ProductServiceImpl (
    private val discountService: DiscountService,
    private val productRepository: ProductRepository
) : ProductService {

    override fun getProducts(category: String?, sortingTerm: String?, descending: Boolean, page: Int, size: Int): Page<Product> {

        val pageable = PageRequest.of(page, size, sortedBy(sortingTerm, descending))

        val productPage = category?.let {
            productRepository.findByCategoryIgnoreCase(it, pageable)
        } ?: productRepository.findAll(pageable)

        val discountedProducts = productPage.map { productEntity ->
            val product = productEntity.toDomain()
            val discount = discountService.calculateDiscount(product)
            val discountedPrice = product.price.subtract(product.price.multiply(discount.percentage))
            product.price = discountedPrice.setScale(2, RoundingMode.DOWN)
            product
        }

        return discountedProducts
    }

    private fun sortedBy(sortingTerm: String?, descending: Boolean): Sort {
        val sortOrder = if (descending) Sort.Direction.DESC else Sort.Direction.ASC
        return when (sortingTerm?.lowercase()) {
            "sku" -> Sort.by(sortOrder, "sku")
            "price" -> Sort.by(sortOrder, "price")
            "description" -> Sort.by(sortOrder, "description")
            "category" -> Sort.by(sortOrder, "category")
            else -> Sort.by(sortOrder, "sku")
        }
    }
}