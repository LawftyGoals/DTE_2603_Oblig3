package com.example.oblig_3.ui.image

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.R
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.ArtVendorViewModel
import com.example.oblig_3.ui.data.Artist
import com.example.oblig_3.ui.data.Category
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.navigation.NavigationDestination


object FilterDestination : NavigationDestination {
    override val route = "filter"
    override val titleRes = R.string.filter_screen
}

@Composable
fun FilterScreen(modifier: Modifier = Modifier,
                     viewModel: ArtVendorViewModel = viewModel(factory = AppViewModelProvider
                         .Factory), navigateToFilteredImages: ()->Unit,
                    navigateBack: ()-> Unit = {}) {
    val uiState by viewModel.uiState.collectAsState()

    val filterContent = if (uiState.chosenFilter == Filters.ARTIST) DataSource.artists else DataSource.categories

    Scaffold(topBar = {
        ArtVendorAppTopBar(
            currentScreen = FilterDestination, canNavigateBack =
                true, navigateUp = navigateBack)
    }){ innerPadding ->
        Column(modifier = modifier.padding(dimensionResource(R.dimen
            .padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))) {
            filterContent.map { filter ->
                CategoryButton(modifier = Modifier.padding(innerPadding), viewModel =
                    viewModel, filter= filter,
                    navigateToFilteredImages = navigateToFilteredImages)
            }

        }

    }
}

@Composable
fun <T> CategoryButton(modifier: Modifier = Modifier, viewModel: ArtVendorViewModel, filter: T,
                       navigateToFilteredImages: () ->
Unit){
    Button(modifier = modifier.fillMaxWidth(), onClick = {
        navigateToFilteredImages()
        viewModel.updateChosenArtistOrCategory(filter)
    }){
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start){
            when (filter) {
                is Artist -> {
                    Text(style=MaterialTheme.typography.labelLarge,text=filter.name)
                    Text(text=filter.familyName)
                }
                is Category -> {
                    Text(text=filter.name)
                }
                else -> {
                    Text(text= stringResource(R.string.unrecognized_filter_type))
                }
            }
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun FilterPreview() {
    FilterScreen(filterContent = DataSource.artists, navigateToFilteredImages = {})
}*/