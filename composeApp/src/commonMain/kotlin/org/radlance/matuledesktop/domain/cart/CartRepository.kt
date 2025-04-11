package org.radlance.matuledesktop.domain.cart

import org.radlance.matuledesktop.domain.remote.FetchResult

interface CartRepository {

    suspend fun cartItems(): FetchResult<List<CartItem>>
}