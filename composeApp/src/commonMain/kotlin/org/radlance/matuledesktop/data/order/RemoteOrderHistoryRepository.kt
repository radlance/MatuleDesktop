package org.radlance.matuledesktop.data.order

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.radlance.matuledesktop.data.database.remote.RemoteMapper
import org.radlance.matuledesktop.data.database.remote.entity.OrderEntity
import org.radlance.matuledesktop.domain.history.Order
import org.radlance.matuledesktop.domain.history.OrderHistoryRepository
import org.radlance.matuledesktop.domain.remote.FetchResult

class RemoteOrderHistoryRepository(
    private val supabaseClient: SupabaseClient
) : OrderHistoryRepository, RemoteMapper() {
    override suspend fun loadHistory(): FetchResult<List<Order>> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Error(null)

        return try {
            val stringResponse = supabaseClient.postgrest.rpc(
                "get_user_orders",
                buildJsonObject { put("p_user_id", user.id) }
            ).data

            val response = Json.decodeFromString<List<OrderEntity>?>(stringResponse)
            FetchResult.Success(response?.map { it.toOrder() } ?: emptyList())

        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }
}