package org.radlance.matuledesktop.presentation.cart

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.domain.user.UserRepository
import org.radlance.matuledesktop.presentation.common.BaseViewModel
import org.radlance.matuledesktop.presentation.common.FetchResultUiState

class OrderViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _userUiState =
        MutableStateFlow<FetchResultUiState<User>>(FetchResultUiState.Initial())
    val userUiState: StateFlow<FetchResultUiState<User>> = _userUiState.onStart {
        updateFetchUiState(_userUiState) {
            userRepository.getCurrentUserData()
        }
    }.stateInViewModel(FetchResultUiState.Initial())
}