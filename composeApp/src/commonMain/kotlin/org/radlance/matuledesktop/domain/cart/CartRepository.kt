package org.radlance.matuledesktop.domain.cart

import org.radlance.matuledesktop.domain.remote.FetchResult

interface CartRepository {

    suspend fun cartItems(): FetchResult<List<CartItem>>

    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): FetchResult<Int>

    suspend fun placeOrder(): FetchResult<Int>
}