package com.example.oblig_3.ui.image

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.R
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.network.ImageSizeDto
import com.example.oblig_3.ui.navigation.NavigationDestination
import com.example.oblig_3.ui.start.StartDestination
import androidx.core.graphics.toColorInt
import coil3.request.crossfade
import com.example.oblig_3.network.FrameSizeDto
import com.example.oblig_3.network.FrameTypeDto
import com.example.oblig_3.ui.convertValueToStringResource


object ImagePreviewDestination : NavigationDestination {
    override val route = "image_preview"
    override val titleRes = R.string.image_preview_screen
    const val IMAGE_ID_ARG = "imageId"
    val routeWithArgs = "${route}/{$IMAGE_ID_ARG}"

}

@Composable
fun ImagePreviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ImagePreviewViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToHomeScreen: () -> Unit,
    navigateBack: () -> Unit = {}
) {

    val previewUiState by viewModel.previewUiState.collectAsState()
    val image = previewUiState.image
    val currentPurchaseItem = previewUiState.currentPurchaseItem

    Scaffold(topBar = {
        ArtVendorAppTopBar(
            currentScreen = StartDestination, canNavigateBack = true, navigateUp = navigateBack
        )
    }) { innerPadding ->
        if (image == null || currentPurchaseItem == null) {
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(
                    text = stringResource(
                        R.string.no_photo_chosen
                    )
                )
            }
        } else {
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_small)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //PHOTO REPRESENTATION
                    ImageContainer(image = image, currentPurchaseItem = currentPurchaseItem)

                    //FIRST ROW OF RADIO BUTTONS
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_small)),
                        style = MaterialTheme.typography.labelLarge,
                        text = stringResource(R.string.choose_border_and_size)
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        SelectFrameType(
                            updatePurchaseItem = { frameType ->
                                val newPurchaseItemDto = currentPurchaseItem.copy()
                                newPurchaseItemDto.frameType = frameType
                                viewModel.updateCurrentPurchaseItem(
                                    newPurchaseItemDto
                                )
                            },
                            selected = currentPurchaseItem.frameType,
                            frames = previewUiState.frameTypes
                        )
                        SelectImageSize(
                            selected = currentPurchaseItem.imageSize,
                            updatePurchaseItem = { imageSize ->
                                val newPurchaseItemDto = currentPurchaseItem.copy()
                                newPurchaseItemDto.imageSize = imageSize
                                viewModel.updateCurrentPurchaseItem(
                                    newPurchaseItemDto
                                )
                            },
                            sizes = previewUiState.imageSizes
                        )
                    }

                    //SECOND ROW OF RADIO BUTTONS
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_small)),
                        style = MaterialTheme.typography.labelLarge,
                        text = stringResource(R.string.chose_border_size)
                    )
                    SelectFrameSize(
                        selected = currentPurchaseItem.frameSize,
                        updatePurchaseItem = { frameSize ->
                            val newPurchaseItemDto = currentPurchaseItem.copy()
                            newPurchaseItemDto.frameSize = frameSize
                            viewModel.updateCurrentPurchaseItem(
                                newPurchaseItemDto
                            )
                        },
                        sizes = previewUiState.frameSizes
                    )

                    //NAVIGATION BUTTONS
                    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))) {
                        Button(onClick = {
                            viewModel.addImageToShoppingCart()
                            navigateToHomeScreen()
                        }) {
                            Text(stringResource(R.string.add_to_cart))
                        }
                        Button(onClick = { navigateToHomeScreen() }) {
                            Text(stringResource(R.string.home))
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SelectFrameType(
    modifier: Modifier = Modifier,
    selected: FrameTypeDto,
    frames: List<FrameTypeDto>,
    updatePurchaseItem: (FrameTypeDto) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
    ) {
        frames.map { frame ->
            Row(
                modifier = Modifier
                    .testTag(frame.name)
                    .selectable(selected = frame.id == selected.id, onClick = {
                        updatePurchaseItem(frame)
                    }, role = Role.RadioButton),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                RadioButton(
                    selected = frame == selected, onClick = null
                )
                Text(text = stringResource(convertValueToStringResource(frame.name)))

            }
        }
    }
}

@Composable
fun SelectImageSize(
    modifier: Modifier = Modifier,
    sizes: List<ImageSizeDto>,
    selected: ImageSizeDto,
    updatePurchaseItem: (ImageSizeDto) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
    ) {
        sizes.map { size ->
            Row(
                modifier = Modifier.selectable(selected = size.size == selected.size, onClick = {
                    updatePurchaseItem(size)
                }, role = Role.RadioButton),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                RadioButton(selected = size == selected, onClick = null)
                Text(text = stringResource(convertValueToStringResource(size.name)))
            }
        }

    }
}

@Composable
fun SelectFrameSize(
    modifier: Modifier = Modifier,
    sizes: List<FrameSizeDto>,
    selected: FrameSizeDto,
    updatePurchaseItem: (FrameSizeDto) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        sizes.map { frameSize ->
            Row(
                modifier = Modifier.selectable(selected = frameSize == selected, onClick = {
                    updatePurchaseItem(frameSize)
                }, role = Role.RadioButton),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                RadioButton(selected = frameSize.size == selected.size, onClick = null)
                Text(text = frameSize.size.toString())

            }
        }
    }
}

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier, image: ImageForUi, currentPurchaseItem: PurchaseItemDto
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_small))
            )
            .padding(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = image.category.name)
        Box(
            modifier = Modifier.border(
                width = currentPurchaseItem.frameSize.size.dp,
                color = Color(currentPurchaseItem.frameType.color.replace("0x", "#").toColorInt())
            )
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = image.imageThumbUrl,
                error = painterResource(R.drawable.ic_launcher_background),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Fit,
                contentDescription = "${image.title} ${currentPurchaseItem.image.artist.firstName} ${currentPurchaseItem.image.artist.lastName}"
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ImagePreviewPreview() {
    ImagePreviewScreen(
        photo = DataSource.photos[0],
        modifier = Modifier,
        currentPurchaseItem = PurchaseItemDto(photo = DataSource.photos[0]),
        onNextButtonClicked = { }
    )
}*/