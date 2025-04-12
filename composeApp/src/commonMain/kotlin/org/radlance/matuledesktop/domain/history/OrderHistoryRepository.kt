package org.radlance.matuledesktop.domain.history

import org.radlance.matuledesktop.domain.remote.FetchResult

interface OrderHistoryRepository {

    suspend fun loadHistory(): FetchResult<List<Order>>
}