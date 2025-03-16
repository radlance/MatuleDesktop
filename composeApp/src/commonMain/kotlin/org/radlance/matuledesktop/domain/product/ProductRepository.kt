package org.radlance.matuledesktop.domain.product

import org.radlance.matuledesktop.domain.remote.FetchResult

interface ProductRepository {
    suspend fun fetchCatalogContent(): FetchResult<CatalogFetchContent>
    suspend fun changeFavoriteStatus(productId: Int): FetchResult<Int>
    suspend fun addProductToCart(productId: Int): FetchResult<Int>
}
