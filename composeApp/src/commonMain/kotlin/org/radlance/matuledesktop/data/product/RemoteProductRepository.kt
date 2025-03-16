package org.radlance.matuledesktop.data.product

import com.radlance.matule.data.database.remote.entity.FavoriteEntity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import org.radlance.matuledesktop.data.database.remote.RemoteMapper
import org.radlance.matuledesktop.data.database.remote.entity.CartEntity
import org.radlance.matuledesktop.data.database.remote.entity.RemoteCategoryEntity
import org.radlance.matuledesktop.data.database.remote.entity.RemoteProductEntity
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
            val categories =
                supabaseClient.from("category").select().decodeList<RemoteCategoryEntity>()
            val products = supabaseClient.from("product").select().decodeList<RemoteProductEntity>()
            val favoriteProducts = supabaseClient.from("favorite")
                .select { filter { FavoriteEntity::userId eq user.id } }
                .decodeList<FavoriteEntity>().associateBy { it.productId }
            val cartProducts =
                supabaseClient.from("cart").select { filter { CartEntity::userId eq user.id } }
                    .decodeList<CartEntity>().associateBy { it.productId }

            FetchResult.Success(
                CatalogFetchContent(
                    categories = categories.map { it.toCategory() },
                    products = products.map { product ->
                        product.toProduct(
                            isFavorite = favoriteProducts.containsKey(product.id),
                            quantityInCart = cartProducts[product.id]?.quantity ?: 0
                        )
                    }
                )
            )
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }


    override suspend fun changeFavoriteStatus(productId: Int): FetchResult<Int> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(productId)

        return try {
            val favorites = supabaseClient.from("favorite")
                .select {
                    filter {
                        FavoriteEntity::productId eq productId
                        FavoriteEntity::userId eq user.id
                    }
                }
                .decodeList<FavoriteEntity>()

            if (favorites.isNotEmpty()) {
                supabaseClient.from("favorite").delete {
                    filter {
                        FavoriteEntity::productId eq productId
                        FavoriteEntity::userId eq user.id
                    }
                }
            } else {
                supabaseClient.from("favorite").insert(
                    FavoriteEntity(productId = productId, userId = user.id)
                )
            }

            FetchResult.Success(productId)
        } catch (e: Exception) {
            FetchResult.Error(productId)
        }
    }

    override suspend fun addProductToCart(productId: Int): FetchResult<Int> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(productId)
        return try {
            val cartEntity = CartEntity(productId = productId, userId = user.id, quantity = 1)
            supabaseClient.from("cart").upsert(cartEntity)
            FetchResult.Success(productId)
        } catch (e: Exception) {
            FetchResult.Error(productId)
        }
    }
}
