package org.radlance.matuledesktop.data.auth

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.HttpRequestException
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.radlance.matuledesktop.domain.auth.AuthRepository
import org.radlance.matuledesktop.domain.auth.AuthResult
import org.radlance.matuledesktop.domain.auth.User

class AuthRepositoryImpl(
    private val auth: Auth
) : AuthRepository {
    override suspend fun signUp(user: User): AuthResult {
        return try {
            auth.signUpWith(Email) {
                email = user.email
                password = user.password
                data = buildJsonObject {
                    put("name", user.firstName)
                    put(
                        "avatar_url",
                        "https://zwhuwripdbtivgxewlsc.supabase.co/storage/v1/object/public/Eventum/default_profile_image.jpg"
                    )
                    put("last_name", "")
                    put("address", "")
                    put("phone_number", "")
                }
            }

            AuthResult.Success

        } catch (e: AuthRestException) {
            AuthResult.Error(statusCode = 422)
        } catch (e: Exception) {
            AuthResult.Error(noConnection = e is HttpRequestException)
        }
    }

    override suspend fun signIn(user: User): AuthResult {
        return try {
            auth.signInWith(Email) {
                email = user.email
                password = user.password
            }
            AuthResult.Success
        } catch (e: AuthRestException) {
            AuthResult.Error(statusCode = 400)
        } catch (e: Exception) {
            AuthResult.Error(noConnection = e is HttpRequestException)
        }
    }
}