package com.example.oblig_3.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import com.example.oblig_3.R


import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.Photo

@Composable
fun ImageScreen(modifier: Modifier = Modifier, photos: List<Photo> = listOf()) {
    LazyVerticalGrid(GridCells.Adaptive(minSize=100.dp)) {
        if(photos.count() < 1){
            item{
                Text(stringResource(R.string.no_images_found))
            }
        }
        else {
            items(photos) { photo ->
                val image = painterResource(photo.imageResId)
                Column {
                    Image(
                        painter = image, contentDescription = "${photo.title} ${
                            photo
                                .artist.name
                        } ${photo.artist.familyName}"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreview(){
    Column{
        ImageScreen()
        ImageScreen(photos = DataSource.photos)
    }
}