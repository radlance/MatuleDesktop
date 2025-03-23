package org.radlance.matuledesktop.domain.product

data class CatalogFetchContent(
    val categories: List<Category>,
    val originCountries: List<OriginCountry>,
    val brands: List<Brand>,
    val sizes: List<Size>,
    val claspTypes: List<ClaspType>,
    val products: List<Product>
)
