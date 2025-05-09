package org.radlance.matuledesktop.data.product

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.radlance.matuledesktop.data.database.remote.RemoteMapper
import org.radlance.matuledesktop.data.database.remote.entity.RpcCatalogResponse
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.ProductRepository
import org.radlance.matuledesktop.domain.remote.FetchResult

class RemoteProductRepository(
    private val supabaseClient: SupabaseClient
) : ProductRepository, RemoteMapper() {
    override suspend fun fetchCatalogContent(): FetchResult<CatalogFetchContent> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(null)

        return try {
            val stringResponse = supabaseClient.postgrest.rpc(
                "fetch_catalog_content",
                buildJsonObject { put("user_id", user.id) }
            ).data

            val response = Json.decodeFromString<RpcCatalogResponse>(stringResponse)

            with(response) {
                FetchResult.Success(
                    CatalogFetchContent(
                        categories = categories.map { it.toCategory() },
                        products = products.map { product ->
                            product.toProduct(
                                isFavorite = favoriteProducts?.containsKey(product.id.toString())
                                    ?: false
                            )
                        },
                        originCountries = originCountries.map { it.toOriginCountry() },
                        sizes = sizes.map { it.toSize() },
                        brands = brands.map { it.toBrand() },
                        claspTypes = claspTypes.map { it.toClaspType() },
                        moistureProtectionTypes = moistureProtectionTypes.map { it.toMoistureProtectionType() },
                        colors = colors.map { it.toColor() }
                    )
                )
            }
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }


    override suspend fun changeFavoriteStatus(productId: Int): FetchResult<Int> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(productId)

        return try {
            supabaseClient.postgrest.rpc(
                "toggle_favorite",
                buildJsonObject {
                    put("p_product_id", productId)
                    put("p_user_id", user.id)
                }
            )

            FetchResult.Success(productId)
        } catch (e: Exception) {
            FetchResult.Error(productId)
        }
    }

    override suspend fun addProductToCart(productId: Int, sizeId: Int): FetchResult<Int> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(productId)

        return try {
            supabaseClient.postgrest.rpc(
                "add_product_to_cart",
                buildJsonObject {
                    put("p_user_id", user.id)
                    put("p_product_id", productId)
                    put("p_size_id", sizeId)
                }
            )

            FetchResult.Success(productId)
        } catch (e: Exception) {
            FetchResult.Error(productId)
        }
    }
}
