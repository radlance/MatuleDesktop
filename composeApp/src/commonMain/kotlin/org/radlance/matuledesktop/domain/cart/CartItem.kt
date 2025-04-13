package org.radlance.matuledesktop.domain.cart

data class CartItem(
    val productId: Int,
    val productSize: Int,
    val cartQuantity: Int,
    val stockQuantity: Int,
    val id: Int
)