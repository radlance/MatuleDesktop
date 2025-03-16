package org.radlance.matuledesktop.presentation.common

import org.radlance.matuledesktop.domain.remote.FetchResult

class FetchResultMapper<T> : FetchResult.Mapper<FetchResultUiState<T>, T> {
    override fun mapError(data: T?): FetchResultUiState<T> {
        return FetchResultUiState.Error(data)
    }

    override fun mapSuccess(data: T): FetchResultUiState<T> {
        return FetchResultUiState.Success(data)
    }

    override fun mapUnauthorized(): FetchResultUiState<T> {
        return FetchResultUiState.Unauthorized()
    }
}