package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.runtime.Composable
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

interface SearchState {

    @Composable
    fun Show(fetchContent: CatalogFetchContent)

    fun inverse(): SearchState

    object Expanded : SearchState {

        @Composable
        override fun Show(fetchContent: CatalogFetchContent) = SearchSettingsPlane(fetchContent)


        override fun inverse(): SearchState = Collapsed
    }

    object Collapsed : SearchState {

        @Composable
        override fun Show(fetchContent: CatalogFetchContent) = Unit

        override fun inverse(): SearchState = Expanded
    }
}