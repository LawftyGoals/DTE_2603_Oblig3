package com.example.oblig_3.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.FrameSize
import com.example.oblig_3.ui.data.FrameType
import com.example.oblig_3.ui.data.Photo

@Composable
fun ImageScreen(
    modifier: Modifier = Modifier,
    photos: List<Photo> = listOf(),
    onNextButtonClick: (Photo) -> Unit = {}
) {

    Column(modifier
        .fillMaxSize()
        .padding(dimensionResource(R.dimen.padding_small))) {
        if (photos.count() < 1) {
            Text(stringResource(R.string.no_images_found))
        } else {
            LazyVerticalGrid(modifier = Modifier.weight(1f), columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)), verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))) {
                items(items = photos) { photo ->
                    ImageCard(photo, onNextButtonClick)
                }
            }
        }
    }
}

@Composable
fun ImageCard(photo: Photo, onNextButtonClick: (Photo) -> Unit){
    val image = painterResource(photo.imageResId)
    Column(modifier = Modifier.testTag("photo-${photo.id}").height(200.dp).background(
        MaterialTheme.colorScheme.tertiaryContainer, shape=RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_small))
    ).padding(dimensionResource(R.dimen.padding_small)).clickable { onNextButtonClick(photo) }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text=photo.category.name)
        Image(
            modifier = Modifier.border((FrameSize.SMALL.size).dp,
                FrameType.WOOD.color).fillMaxWidth(), contentScale = ContentScale.FillWidth,
            painter = image, contentDescription = "${photo.title} ${
                photo
                    .artist.name
            } ${photo.artist.familyName}"
        )

    }

}

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    Column {
        //ImageScreen()
        ImageScreen(photos = DataSource.photos)
    }
}