package org.radlance.matuledesktop.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import org.radlance.matuledesktop.domain.cart.CartItem
import org.radlance.matuledesktop.domain.cart.CartRepository
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {

    private val _catalogContent =
        MutableStateFlow<FetchResultUiState<CatalogFetchContent>>(FetchResultUiState.Loading())
    val catalogContent: StateFlow<FetchResultUiState<CatalogFetchContent>> =
        _catalogContent.onStart { fetchContent() }.stateInViewModel(
            FetchResultUiState.Loading()
        )

    private val _cartContent =
        MutableStateFlow<FetchResultUiState<List<CartItem>>>(FetchResultUiState.Initial())
    val cartContent: StateFlow<FetchResultUiState<List<CartItem>>> = _cartContent.onStart {
        fetchCartItems()
    }.stateInViewModel(FetchResultUiState.Initial())

    private val _favoriteResult =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val favoriteResult: StateFlow<FetchResultUiState<Int>>
        get() = _favoriteResult.asStateFlow()

    private val _inCartResult =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val inCartResult: StateFlow<FetchResultUiState<Int>>
        get() = _inCartResult.asStateFlow()

    private val _quantityResult =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val quantityResult: StateFlow<FetchResultUiState<Int>>
        get() = _quantityResult.asStateFlow()

    private val _placeOrderResultUIState =
        MutableStateFlow<FetchResultUiState<Int>>(FetchResultUiState.Initial())
    val placeOrderResultUIState: StateFlow<FetchResultUiState<Int>>
        get() = _placeOrderResultUIState.asStateFlow()

    fun fetchContent() {
        updateFetchUiState(_catalogContent) { productRepository.fetchCatalogContent() }
    }

    fun fetchCartItems() {
        updateFetchUiState(stateFlow = _cartContent) { cartRepository.cartItems() }
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

    fun updateProductQuantity(cartItemId: Int, currentQuantity: Int) {
        updateFetchUiState(stateFlow = _quantityResult, loadingData = cartItemId) {
            cartRepository.updateCartItemQuantity(cartItemId, currentQuantity)
        }
    }

    fun placeOrder() {
        updateFetchUiState(stateFlow = _placeOrderResultUIState) { cartRepository.placeOrder() }
    }

    fun resetPlaceOrderState() {
        _placeOrderResultUIState.value = FetchResultUiState.Initial()
    }

    fun changeStateFavoriteStatus(productId: Int) {
        updateLocalState(_catalogContent) { currentState ->
            changeFavoriteByResult(currentState, productId)
        }
    }

    fun updateCurrentQuantity(cartItemId: Int, increment: Boolean) {
        updateLocalState(_cartContent) { currentState ->
            updateCartItemQuantity(currentState, cartItemId, increment)
        }
    }

    private fun updateCartItemQuantity(
        currentState: FetchResultUiState.Success<List<CartItem>>,
        cartItemId: Int,
        increment: Boolean
    ) {
        val updatedProducts = currentState.data.map { cartItem ->
            if (cartItem.id == cartItemId) {
                val newQuantity =
                    if (increment) cartItem.quantity.inc() else cartItem.quantity.dec()
                cartItem.copy(quantity = newQuantity)
            } else {
                cartItem
            }
        }

        val updatedState = currentState.copy(data = updatedProducts)
        _cartContent.value = updatedState
    }

    fun changeLocalCartState(
        productId: Int,
        size: Int,
        reverse: Boolean = false
    ) {
        updateLocalState(_cartContent) { currentState ->
            changeCartByResult(currentState, productId, size, reverse)
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

    private fun changeCartByResult(
        currentState: FetchResultUiState.Success<List<CartItem>>,
        productId: Int,
        size: Int,
        reverse: Boolean,
    ) {
        val updatedCartItems = currentState.data.map { cartItem ->
            if (cartItem.productId == productId && cartItem.productSize == size) {
                cartItem.copy(
                    quantity = if (reverse) {
                        cartItem.quantity.dec()
                    } else {
                        cartItem.quantity.inc()
                    }
                )
            } else {
                cartItem
            }
        }

        val updatedContent = currentState.copy(data = updatedCartItems)
        _cartContent.value = updatedContent
    }
}
