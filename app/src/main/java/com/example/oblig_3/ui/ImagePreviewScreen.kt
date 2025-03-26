package com.example.oblig_3.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.Photo


@Composable
fun ImagePreviewScreen(modifier: Modifier = Modifier, photo: Photo) {
    val image = painterResource(photo.imageResId)
    Column {
        Row {

            Box(modifier = Modifier.border(width=4.dp, color = Color.Blue )){
                Image(painter = image, contentDescription = "${photo.title} ${photo
                .artist.name} ${photo.artist.familyName}") }
        }
        Text(text= stringResource(R.string.choose_border_and_size))
        Row {
            Column {
                DataSource.frames.map { frame ->
                    Text(text=frame.name)
                }
            }
            Column {
                DataSource.photoSizes.map { size ->
                    Text(text=size.name)
                }

            }
        }
        Text(text= stringResource(R.string.chose_border_size))
        Row {
            DataSource.frameSizes.map{ size ->
                Text(text=size.toString())
            }
        }
        Row {
            Button(onClick = {Log.i("IMAGEPREVIEWSCREEN", "onclick")}) {
                Text(stringResource(R.string.placeholder))
            }
            Button(onClick = {Log.i("IMAGEPREVIEWSCREEN", "onclick")}) {
                Text(stringResource(R.string.placeholder))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreviewPreview() {
    ImagePreviewScreen(photo = DataSource.photos[0])
}