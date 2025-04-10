package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductSizeEntity(
    val sizeId: Int,
    val size: Int,
    val quantity: Int
)