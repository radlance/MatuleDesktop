package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RpcCatalogResponse(
    val categories: List<CategoryEntity>,
    val products: List<ProductEntity>,
    val originCountries: List<OriginCountryEntity>,
    val brands: List<BrandEntity>,
    val sizes: List<SizeEntity>,
    val claspTypes: List<ClaspTypeEntity>,
    val moistureProtectionTypes: List<MoistureProtectionTypeEntity>,
    val colors: List<ColorEntity>,
    val favoriteProducts: Map<String, FavoriteEntity>?
)