package org.radlance.matuledesktop.data.user

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.Json
import org.radlance.matuledesktop.data.database.remote.RemoteMapper
import org.radlance.matuledesktop.data.database.remote.entity.UserEntity
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.domain.remote.FetchResult
import org.radlance.matuledesktop.domain.user.UserRepository

class RemoteUserRepository(
    private val supabaseClient: SupabaseClient
) : UserRepository, RemoteMapper() {
    override suspend fun getCurrentUserData(): FetchResult<User> {
        val user =
            supabaseClient.auth.currentSessionOrNull()?.user ?: return FetchResult.Unauthorized()

        return try {
            val json = Json { ignoreUnknownKeys = true }
            val userData = user.userMetadata?.let {
                json.decodeFromString<UserEntity>(
                    it.toString()
                ).toUser()
            }
            FetchResult.Success(userData?.copy(email = user.email ?: "") ?: User())
        } catch (e: Exception) {
            FetchResult.Error(null)
        }
    }
}