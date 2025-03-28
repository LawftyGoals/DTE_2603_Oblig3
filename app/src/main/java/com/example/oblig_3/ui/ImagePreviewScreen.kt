package com.example.oblig_3.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


@Composable
fun ImagePreviewScreen(
    modifier: Modifier = Modifier,
    photo: Photo?,
    onNextButtonClicked: (PurchaseItem?) -> Unit
) {

    var purchaseItem by remember { mutableStateOf(PurchaseItem(photo)) }

    if (photo == null) {
        Text(stringResource(R.string.no_photo_chosen))
    } else {
        val image = painterResource(photo.imageResId)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {

                Box(modifier = Modifier.border(width = 4.dp, color = purchaseItem.frameType.color)) {
                    Image(
                        painter = image, contentDescription = "${photo.title} ${
                            photo
                                .artist.name
                        } ${photo.artist.familyName}"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), text = stringResource(R.string.choose_border_and_size)
            )
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                SelectFrameType(updatePurchaseItem = { frameType ->
                    purchaseItem =
                        PurchaseItem(photo, purchaseItem.size, frameType, purchaseItem.frameSize)
                })
                SelectPhotoSize(updatePurchaseItem = { photoSize ->
                    purchaseItem = PurchaseItem(
                        photo,
                        photoSize,
                        purchaseItem.frameType,
                        purchaseItem.frameSize
                    )
                })
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), text = stringResource(R.string.chose_border_size)
            )
            SelectFrameSize(updatePurchaseItem = { frameSize ->
                purchaseItem =
                    PurchaseItem(photo, purchaseItem.size, purchaseItem.frameType, frameSize)
            })
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    //updatePurchaseItemState(purchaseItem)
                    onNextButtonClicked(purchaseItem)
                    Log.i("PURCHASEITEM", "${purchaseItem.frameType}")
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
    frames: List<FrameType> = DataSource.frames,
    updatePurchaseItem: (FrameType) -> Unit
) {
    var selected by remember { mutableStateOf(frames[0]) }
    Column {
        DataSource.frames.map { frame ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = frame == selected, onClick = {
                    selected = frame
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
    updatePurchaseItem: (PhotoSize) -> Unit
) {

    var selected by remember { mutableStateOf(sizes[0]) }
    Column {
        DataSource.photoSizes.map { size ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = size == selected, onClick = {
                    selected = size
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
    updatePurchaseItem: (FrameSize) -> Unit
) {

    var selected by remember { mutableIntStateOf(sizes[0].size) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DataSource.frameSizes.map { frameSize ->
            RadioButton(selected = frameSize.size == selected, onClick = {
                selected = frameSize.size
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
        modifier = TODO(),
        onNextButtonClicked = TODO()
    )
}