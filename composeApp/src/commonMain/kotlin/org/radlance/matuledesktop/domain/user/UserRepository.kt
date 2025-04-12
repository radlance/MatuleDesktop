package org.radlance.matuledesktop.domain.user

import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.domain.remote.FetchResult

interface UserRepository {
    suspend fun getCurrentUserData(): FetchResult<User>
}