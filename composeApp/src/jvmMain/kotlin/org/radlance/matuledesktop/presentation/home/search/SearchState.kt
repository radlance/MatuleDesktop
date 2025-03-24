package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.runtime.Composable
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

internal interface SearchState {

    @Composable
    fun Show(
        fetchContent: CatalogFetchContent,
        onCheckOriginCountry: (List<Int>) -> Unit,
        onCheckBrand: (List<Int>) -> Unit,
        onCheckSize: (List<Int>) -> Unit,
        onCheckClaspType: (List<Int>) -> Unit,
        onCheckMoistureProtectionType: (List<Int>) -> Unit,
        onCheckColor: (List<Int>) -> Unit,
    )

    fun inverse(): SearchState

    object Expanded : SearchState {

        @Composable
        override fun Show(
            fetchContent: CatalogFetchContent,
            onCheckOriginCountry: (List<Int>) -> Unit,
            onCheckBrand: (List<Int>) -> Unit,
            onCheckSize: (List<Int>) -> Unit,
            onCheckClaspType: (List<Int>) -> Unit,
            onCheckMoistureProtectionType: (List<Int>) -> Unit,
            onCheckColor: (List<Int>) -> Unit
        ) {
            SearchSettingsPlane(
                fetchContent = fetchContent,
                onCheckOriginCountry = onCheckOriginCountry,
                onCheckBrand = onCheckBrand,
                onCheckSize = onCheckSize,
                onCheckClaspType = onCheckClaspType,
                onCheckMoistureProtectionType = onCheckMoistureProtectionType,
                onCheckColor = onCheckColor
            )
        }

        override fun inverse(): SearchState = Collapsed
    }

    object Collapsed : SearchState {

        @Composable
        override fun Show(
            fetchContent: CatalogFetchContent,
            onCheckOriginCountry: (List<Int>) -> Unit,
            onCheckBrand: (List<Int>) -> Unit,
            onCheckSize: (List<Int>) -> Unit,
            onCheckClaspType: (List<Int>) -> Unit,
            onCheckMoistureProtectionType: (List<Int>) -> Unit,
            onCheckColor: (List<Int>) -> Unit
        ) = Unit

        override fun inverse(): SearchState = Expanded
    }
}