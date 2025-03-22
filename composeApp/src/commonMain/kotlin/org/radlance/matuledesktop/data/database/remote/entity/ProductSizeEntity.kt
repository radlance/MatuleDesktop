package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductSizeEntity(
    @SerialName("size") val size: Int,
    val quantity: Int
)