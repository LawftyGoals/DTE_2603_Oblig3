package com.example.oblig_3.ui.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.R
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.navigation.NavigationDestination


object FilterDestination : NavigationDestination {
    override val route = "filter"
    override val titleRes = R.string.filter_screen
    const val FILTER_TYPE_ARG = "filterType"
    val routeWithArgs = "$route/{$FILTER_TYPE_ARG}"
}

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier, viewModel: FilterViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ), navigateToFilteredImages: (String, Int) -> Unit, navigateBack: () -> Unit = {}
) {

    val filterUiState by viewModel.filterUiState.collectAsState()
    val filterType = filterUiState.filterType

    val filterContent: List<Any> =
        (if (filterType == Filters.ARTIST.name) filterUiState.artistList else filterUiState.categoryList)

    Scaffold(
        topBar = {
            ArtVendorAppTopBar(
                currentScreen = FilterDestination, canNavigateBack = true, navigateUp = navigateBack
            )
        }) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
            ) {
                filterContent.map { filter ->
                    CategoryButton(
                        filter = filter, navigateToFilteredImages = navigateToFilteredImages
                    )
                }

            }
        }

    }
}

@Composable
fun <T> CategoryButton(
    modifier: Modifier = Modifier, filter: T, navigateToFilteredImages: (String, Int) -> Unit
) {
    when (filter) {
        is ArtistDto -> {
            Button(modifier = modifier.fillMaxWidth(), onClick = {
                navigateToFilteredImages(Filters.ARTIST.name, filter.id)
            }) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(style = MaterialTheme.typography.labelLarge, text = filter.firstName)
                    Text(text = filter.lastName)
                }
            }
        }

        is CategoryDto -> {
            Button(modifier = modifier.fillMaxWidth(), onClick = {
                navigateToFilteredImages(Filters.CATEGORY.name, filter.id)
            }) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(text = filter.name)
                }
            }
        }

        else -> {
            Text(text = stringResource(R.string.unrecognized_filter_type))
        }
    }


}


/*
@Preview(showBackground = true)
@Composable
fun FilterPreview() {
    FilterScreen(filterContent = DataSource.artists, navigateToFilteredImages = {})
}*/