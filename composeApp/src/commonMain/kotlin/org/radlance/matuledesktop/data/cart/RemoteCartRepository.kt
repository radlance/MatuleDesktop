package org.radlance.matuledesktop.data.cart

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.radlance.matuledesktop.data.database.remote.RemoteMapper
import org.radlance.matuledesktop.data.database.remote.entity.CartItemEntity
import org.radlance.matuledesktop.domain.cart.CartItem
import org.radlance.matuledesktop.domain.cart.CartRepository
import org.radlance.matuledesktop.domain.remote.FetchResult

class RemoteCartRepository(
    private val supabaseClient: SupabaseClient
) : CartRepository, RemoteMapper() {
    override suspend fun cartItems(): FetchResult<List<CartItem>> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(null)

        return try {
            val stringResponse = supabaseClient.postgrest.rpc(
                "fetch_cart_items",
                buildJsonObject {
                    put("p_user_id", user.id)
                }
            ).data

            val response = Json.decodeFromString<List<CartItemEntity>?>(stringResponse)
            FetchResult.Success(response?.map { it.toCartItem() } ?: emptyList())

        } catch (e: Exception) {
            println(e.message)
            FetchResult.Error(null)
        }
    }

    override suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): FetchResult<Int> {
        return try {
            supabaseClient.from("cart_item").update(
                {
                    CartItemEntity::quantity setTo quantity
                }
            ) {
                filter {
                    CartItemEntity::id eq cartItemId
                }
            }

            FetchResult.Success(cartItemId)
        } catch (e: Exception) {
            FetchResult.Error(cartItemId)
        }
    }

    override suspend fun placeOrder(): FetchResult<Int> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(null)

        return try {
            val stringResponse = supabaseClient.postgrest.rpc(
                "place_order",
                buildJsonObject {
                    put("user_id", user.id)
                }
            ).data

            FetchResult.Success(stringResponse.toInt())

        } catch (e: Exception) {
            println(e.message)
            FetchResult.Error(null)
        }
    }
}