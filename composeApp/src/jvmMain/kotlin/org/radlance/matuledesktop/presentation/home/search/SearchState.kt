package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.runtime.Composable
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

interface SearchState {

    @Composable
    fun Show(fetchContent: CatalogFetchContent, onCheckOriginCountry: (List<Int>) -> Unit)

    fun inverse(): SearchState

    object Expanded : SearchState {

        @Composable
        override fun Show(
            fetchContent: CatalogFetchContent,
            onCheckOriginCountry: (List<Int>) -> Unit
        ) {
            SearchSettingsPlane(
                fetchContent = fetchContent,
                onCheckOriginCountry = onCheckOriginCountry
            )
        }

        override fun inverse(): SearchState = Collapsed
    }

    object Collapsed : SearchState {

        @Composable
        override fun Show(
            fetchContent: CatalogFetchContent,
            onCheckOriginCountry: (List<Int>) -> Unit
        ) = Unit

        override fun inverse(): SearchState = Expanded
    }
}