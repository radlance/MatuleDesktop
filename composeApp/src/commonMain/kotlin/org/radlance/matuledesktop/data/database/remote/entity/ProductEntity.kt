package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductEntity(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("category_id") val categoryId: Int,
    @SerialName("is_popular") val isPopular: Boolean,
    @SerialName("origin_country_id") val originCountryId: Int,
    @SerialName("brand_id") val brandId: Int,
    @SerialName("model_name") val modelName: String,
    @SerialName("clasp_type_id") val claspTypeId: Int,
    val sizes: List<ProductSizeEntity>
)