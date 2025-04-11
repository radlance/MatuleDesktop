package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class CartItemEntity(
    val id: Int,
    val productId: Int,
    val productSize: Int,
    val quantity: Int
)
