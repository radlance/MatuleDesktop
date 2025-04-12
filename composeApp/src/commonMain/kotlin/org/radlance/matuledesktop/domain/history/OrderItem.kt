package org.radlance.matuledesktop.domain.history

data class OrderItem(
    val orderId: Int,
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val priceAtPurchase: Double
)