package org.radlance.matuledesktop.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.radlance.matuledesktop.domain.remote.FetchResult

abstract class BaseViewModel : ViewModel() {
    protected fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialValue
        )
    }

    protected inline fun <T> updateFetchUiState(
        stateFlow: MutableStateFlow<FetchResultUiState<T>>,
        loadingData: T? = null,
        crossinline fetch: suspend () -> FetchResult<T>
    ) {
        viewModelScope.launch {
            stateFlow.value = FetchResultUiState.Loading(loadingData)
            delay(100)
            stateFlow.value = fetch().map(FetchResultMapper())
        }
    }

    protected inline fun <T> updateLocalState(
        state: StateFlow<FetchResultUiState<T>>,
        action: (FetchResultUiState.Success<T>) -> Unit
    ) {
        val value = state.value

        if (value is FetchResultUiState.Success) {
            action(value)
        }
    }
}