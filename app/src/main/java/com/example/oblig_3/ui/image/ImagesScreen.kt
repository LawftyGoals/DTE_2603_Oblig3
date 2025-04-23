package com.example.oblig_3.ui.image

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.example.oblig_3.R


import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.data.FrameSize
import com.example.oblig_3.ui.data.FrameType
import com.example.oblig_3.ui.navigation.NavigationDestination


object ImagesDestination : NavigationDestination {
    override val route = "images"
    override val titleRes = R.string.images_screen
    const val FILTER_TYPE_ARG = "filterType"
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$FILTER_TYPE_ARG}/{$ID_ARG}"
}


@Composable
fun ImagesScreen(
    modifier: Modifier = Modifier, viewModel: ImagesViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ), navigateToImagePreview: (Int) -> Unit = {}, navigateBack: () -> Unit = {}
) {
    val imageUiState by viewModel.imagesUiState.collectAsState()
    val images = imageUiState.filteredImages

    Scaffold(topBar = {
        ArtVendorAppTopBar(
            currentScreen = ImagesDestination, canNavigateBack = true, navigateUp = navigateBack
        )
    }) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            if (images.isEmpty()) {
                Column(modifier = Modifier.padding(innerPadding)) {
                    Text(text = stringResource(R.string.no_images_found), fontSize = 24.sp)
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(innerPadding)
                        .weight(1f),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
                ) {
                    items(items = images) { image ->
                        ImageCard(image, navigateToImagePreview)
                    }
                }
            }
        }
    }

}

@Composable
fun ImageCard(image: ImageForUi, onNextButtonClick: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .testTag("image-${image.id}")
            .height(200.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_small))
            )
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable { onNextButtonClick(image.id) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = image.category.name)
        AsyncImage(
            modifier = Modifier
                .border(
                    (FrameSize.SMALL.size).dp, FrameType.WOOD.color
                )
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = image.imageThumbUrl,
            error = painterResource(R.drawable.ic_launcher_background),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "${image.title} ${
                image.artist.firstName
            } ${image.artist.lastName}"
        )

    }
}/*
@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    Column {
        //ImageScreen()
        ImagesScreen(photos = DataSource.photos)
    }
}*/