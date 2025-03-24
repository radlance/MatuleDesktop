package org.radlance.matuledesktop.domain.product

data class Product(
    val title: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val categoryId: Int,
    val isFavorite: Boolean,
    val quantityInCart: Int,
    val isPopular: Boolean,
    val originCountryId: Int,
    val brandId: Int,
    val sizes: List<ProductSize>,
    val modelName: String,
    val claspTypeId: Int,
    val moistureProtectionTypeId: Int,
    val colors: List<ProductColor>,
    val id: Int = 0
)