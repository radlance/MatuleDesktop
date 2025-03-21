package org.radlance.matuledesktop.domain.product

data class CatalogFetchContent(
    val categories: List<Category>,
    val originCountries: List<OriginCountry>,
    val products: List<Product>
)
