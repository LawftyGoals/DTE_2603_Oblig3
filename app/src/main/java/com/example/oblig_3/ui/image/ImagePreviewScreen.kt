package com.example.oblig_3.ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.FrameSize
import com.example.oblig_3.ui.data.FrameType
import com.example.oblig_3.ui.data.Photo
import com.example.oblig_3.ui.data.PhotoSize
import com.example.oblig_3.ui.data.PurchaseItemDto
import com.example.oblig_3.ui.data.testPhoto
import com.example.oblig_3.ui.navigation.NavigationDestination
import com.example.oblig_3.ui.start.StartDestination


object ImagePreviewDestination : NavigationDestination {
    override val route = "image_preview"
    override val titleRes = R.string.image_preview_screen
}

@Composable
fun ImagePreviewScreen(
    modifier: Modifier = Modifier,
    photo: Photo = testPhoto,
    currentPurchaseItem: PurchaseItemDto,
    updateCurrentPurchaseItem: (PurchaseItemDto) -> Unit = {},
    navigateToHomeScreen: (PurchaseItemDto?) -> Unit,
    navigateBack: ()->Unit = {}
) {

    Scaffold(topBar={ArtVendorAppTopBar(
        currentScreen = StartDestination,
        canNavigateBack = true,
        navigateUp = navigateBack
    )}){ innerPadding ->
    if (photo.id == -1L) {
        Text(modifier = Modifier.padding(innerPadding), text = stringResource(R.string
            .no_photo_chosen))
    } else {
        val image = painterResource(photo.imageResId)

        Column(
            modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //PHOTO REPRESENTATION
            ImageContainer(image = image, photo = photo, currentPurchaseItem = currentPurchaseItem)

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
                        updateCurrentPurchaseItem(
                            PurchaseItemDto(
                                id = 1,
                                photo = photo,
                                photoSize = currentPurchaseItem.photoSize,
                                frameType = frameType,
                                frameSize = currentPurchaseItem.frameSize)

                        )
                    },
                    selected = currentPurchaseItem.frameType
                )
                SelectPhotoSize(
                    selected = currentPurchaseItem.photoSize,
                    updatePurchaseItem = { photoSize ->
                        updateCurrentPurchaseItem(
                            PurchaseItemDto(
                                id = 1,
                                photo = photo,
                                photoSize = photoSize,
                                frameType = currentPurchaseItem.frameType,
                                frameSize = currentPurchaseItem.frameSize
                            )
                        )
                    })
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
                    updateCurrentPurchaseItem(
                        PurchaseItemDto(
                            id = 1,
                            photo = photo,
                            photoSize = currentPurchaseItem.photoSize,
                            frameType = currentPurchaseItem.frameType,
                            frameSize = frameSize
                        )
                    )
                })

            //NAVIGATION BUTTONS
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))) {
                Button(onClick = {
                    navigateToHomeScreen(currentPurchaseItem)
                }) {
                    Text(stringResource(R.string.add_to_cart))
                }
                Button(onClick = { navigateToHomeScreen(null) }) {
                    Text(stringResource(R.string.home))
                }

            }
        }
    }
    }
}

@Composable
fun SelectFrameType(
    modifier: Modifier = Modifier,
    selected: FrameType,
    frames: List<FrameType> = DataSource.frames,
    updatePurchaseItem: (FrameType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
    ) {
        frames.map { frame ->
            Row(
                modifier = Modifier
                    .testTag(frame.name)
                    .selectable(selected = frame == selected, onClick = {
                        updatePurchaseItem(frame)
                    }, role = Role.RadioButton),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                RadioButton(
                    selected = frame == selected, onClick = null
                )
                Text(text = stringResource(frame.title))

            }
        }
    }
}

@Composable
fun SelectPhotoSize(
    modifier: Modifier = Modifier, sizes: List<PhotoSize> = DataSource.photoSizes,
    selected: PhotoSize,
    updatePurchaseItem: (PhotoSize) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
    ) {
        sizes.map { size ->
            Row(
                modifier = Modifier.selectable(selected = size == selected, onClick = {
                    updatePurchaseItem(size)
                }, role = Role.RadioButton),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                RadioButton(selected = size == selected, onClick = null)
                Text(text = stringResource(size.title))
            }
        }

    }
}

@Composable
fun SelectFrameSize(
    modifier: Modifier = Modifier, sizes: List<FrameSize> = DataSource.frameSizes,
    selected: FrameSize,
    updatePurchaseItem: (FrameSize) -> Unit
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
    modifier: Modifier = Modifier,
    image: Painter,
    photo: Photo,
    currentPurchaseItem: PurchaseItemDto
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
        Text(text = photo.category.name)
        Box(
            modifier = Modifier.border(
                width = currentPurchaseItem.frameSize.size.dp,
                color = currentPurchaseItem.frameType.color
            )
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height),
                painter = image,
                contentScale = ContentScale.Fit,
                contentDescription = "${photo.title} ${
                    photo
                        .artist.name
                } ${photo.artist.familyName}"
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