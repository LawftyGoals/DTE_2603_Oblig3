package com.example.oblig_3.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.FrameSize
import com.example.oblig_3.ui.data.FrameType
import com.example.oblig_3.ui.data.Photo
import com.example.oblig_3.ui.data.PhotoSize
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.testPhoto


@Composable
fun ImagePreviewScreen(
    modifier: Modifier = Modifier,
    photo: Photo = testPhoto,
    currentPurchaseItem: PurchaseItem,
    updateCurrentPurchaseItem: (PurchaseItem) -> Unit = {},
    onNextButtonClicked: (PurchaseItem?) -> Unit
) {
    if (photo.id == -1L) {
        Text(stringResource(R.string.no_photo_chosen))
    } else {
        val image = painterResource(photo.imageResId)

        Column(modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_small)), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_small))
            ).padding(dimensionResource(R.dimen.padding_small)), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text=photo.category.name)
                Box(
                    modifier = Modifier.border(
                        width = currentPurchaseItem.frameSize.size.dp,
                        color = currentPurchaseItem.frameType.color
                    )
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth().aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height),
                        painter = image, contentScale = ContentScale.Fit, contentDescription = "${photo.title} ${
                            photo
                                .artist.name
                        } ${photo.artist.familyName}"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)), style= MaterialTheme.typography.labelLarge,
                text = stringResource(R.string.choose_border_and_size)
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                SelectFrameType(
                    updatePurchaseItem = { frameType ->
                        updateCurrentPurchaseItem(
                            PurchaseItem(
                                photo,
                                currentPurchaseItem.size,
                                frameType,
                                currentPurchaseItem.frameSize
                            )
                        )
                    },
                    selected = currentPurchaseItem.frameType
                )
                SelectPhotoSize(
                    selected = currentPurchaseItem.size,
                    updatePurchaseItem = { photoSize ->
                        updateCurrentPurchaseItem(
                            PurchaseItem(
                                photo,
                                photoSize,
                                currentPurchaseItem.frameType,
                                currentPurchaseItem.frameSize
                            )
                        )
                    })
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)), style= MaterialTheme.typography.labelLarge,
                text = stringResource(R.string.chose_border_size)
            )
            SelectFrameSize(
                selected = currentPurchaseItem.frameSize,
                updatePurchaseItem = { frameSize ->
                    updateCurrentPurchaseItem(
                        PurchaseItem(
                            photo,
                            currentPurchaseItem.size,
                            currentPurchaseItem.frameType,
                            frameSize
                        )
                    )
                })
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))) {
                Button(onClick = {
                    onNextButtonClicked(currentPurchaseItem)
                }) {
                    Text(stringResource(R.string.add_to_cart))
                }
                Button(onClick = { onNextButtonClicked(null) }) {
                    Text(stringResource(R.string.home))
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
    Column(modifier = modifier) {
        frames.map { frame ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = frame == selected, onClick = {
                    updatePurchaseItem(frame)
                }
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

    Column(modifier = modifier) {
        sizes.map { size ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = size == selected, onClick = {
                    updatePurchaseItem(size)
                })
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        sizes.map { frameSize ->
            RadioButton(selected = frameSize.size == selected.size, onClick = {
                updatePurchaseItem(frameSize)
            })
            Text(text = frameSize.size.toString())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ImagePreviewPreview() {
    ImagePreviewScreen(
        photo = DataSource.photos[0],
        modifier = Modifier,
        currentPurchaseItem = PurchaseItem(DataSource.photos[0]),
        onNextButtonClicked = { }
    )
}