package org.radlance.matuledesktop.domain.auth

interface AuthRepository {
    suspend fun signUp(user: User): AuthResult
    suspend fun signIn(user: User): AuthResult
}