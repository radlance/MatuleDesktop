package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RpcCatalogResponse(
    val categories: List<CategoryEntity>,
    val products: List<ProductEntity>,
    val originCountries: List<OriginCountryEntity>,
    val favoriteProducts: Map<String, FavoriteEntity>,
    val cartProducts: Map<String, CartEntity>
)