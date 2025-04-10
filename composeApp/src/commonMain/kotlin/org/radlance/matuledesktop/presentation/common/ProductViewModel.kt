package org.radlance.matuledesktop.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _catalogContent =
        MutableStateFlow<FetchResultUiState<CatalogFetchContent>>(FetchResultUiState.Loading())
    val catalogContent: StateFlow<FetchResultUiState<CatalogFetchContent>> =
        _catalogContent.onStart { fetchContent() }.stateInViewModel(
            FetchResultUiState.Loading()
        )

    private val _favoriteResult =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val favoriteResult: StateFlow<FetchResultUiState<Int>>
        get() = _favoriteResult.asStateFlow()

    private val _inCartResult =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val inCartResult: StateFlow<FetchResultUiState<Int>>
        get() = _inCartResult.asStateFlow()

    fun fetchContent() {
        updateFetchUiState(_catalogContent) { productRepository.fetchCatalogContent() }
    }

    fun changeFavoriteStatus(productId: Int) {
        updateFetchUiState(stateFlow = _favoriteResult, loadingData = productId) {
            productRepository.changeFavoriteStatus(productId)
        }
    }

    fun addProductToCart(productId: Int, selectedSize: Int) {
        updateFetchUiState(stateFlow = _inCartResult, loadingData = productId) {
            productRepository.addProductToCart(productId, selectedSize)
        }
    }

    fun changeStateFavoriteStatus(productId: Int) {
        updateLocalState(_catalogContent) { currentState ->
            changeFavoriteByResult(currentState, productId)
        }
    }

    private fun changeFavoriteByResult(
        currentState: FetchResultUiState.Success<CatalogFetchContent>,
        productId: Int
    ) {
        val updatedProducts = currentState.data.products.map { product ->
            if (product.id == productId) {
                product.copy(isFavorite = !product.isFavorite)
            } else {
                product
            }
        }
        val updatedContent = currentState.data.copy(products = updatedProducts)
        _catalogContent.value = FetchResultUiState.Success(updatedContent)
    }
}
