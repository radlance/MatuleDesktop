package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemEntity(
    val orderId: Int,
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val priceAtPurchase: Double
)
