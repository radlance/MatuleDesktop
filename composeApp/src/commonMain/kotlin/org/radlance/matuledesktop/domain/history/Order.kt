package org.radlance.matuledesktop.domain.history

import kotlinx.datetime.LocalDateTime

data class Order(
    val id: Int,
    val date: LocalDateTime,
    val totalPrice: Double,
    val orderItems: List<OrderItem>
)
