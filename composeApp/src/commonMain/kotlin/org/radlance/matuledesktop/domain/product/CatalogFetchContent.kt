package org.radlance.matuledesktop.domain.product

data class CatalogFetchContent(
    val categories: List<Category>,
    val originCountries: List<OriginCountry>,
    val brands: List<Brand>,
    val products: List<Product>
)
