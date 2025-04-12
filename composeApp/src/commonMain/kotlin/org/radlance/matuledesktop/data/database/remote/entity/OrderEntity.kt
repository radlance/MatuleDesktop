package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderEntity(
    val id: Int,
    val date: LocalDateTime,
    @SerialName("total_price") val totalPrice: Double,
    @SerialName("order_items") val orderItems: List<OrderItemEntity>
)
