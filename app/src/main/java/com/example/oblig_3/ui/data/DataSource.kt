package com.example.oblig_3.ui.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.oblig_3.R

class DataSource {
    val artists = listOf(
        Artist(1L, "Jack", "Jackson"),
        Artist(2L, "Jane", "Bethdaughter"),
        Artist(3L, "Zack", "Zackson"),
        Artist(4L, "Dame", "Damedaughter")
    )

    val photos = listOf(
        Photo(1L, "My first attempt", R.drawable.image1, artists[0], Category.NATURE, )

    )


}

data class Photo(
    val id: Long,
    val title: String = "",
    @DrawableRes
    val imageResId: Int,
    val artist: Artist,
    val category: Category,
    val price: Float = 0.0f
)

data class Artist(
    val id: Long,
    val name: String = "",
    val familyName: String = ""
)

data class SelectedPhoto(
    val photoId: Long,
    val frameType: FrameType,
    val frameWidth: Int,
    val photoSize: PhotoSize,
    val photoPrice: Float = 0.0f,
)

enum class Category {
    NATURE(),
    FOOD(),
    SPORT()
}

enum class FrameType(val extraPrice: Float, val color: Color = Color.Yellow) {
    WOOD(0f, color = Color.Yellow),
    METAL(100f, color = Color.Blue),
    PLASTIC(30f, color = Color.Green)
}

enum class PhotoSize(val extraPrice: Float, val size: Int = 170) {
    SMALL(0f, size=170),
    MEDIUM(130f, size=200),
    LARGE(230f, size=250)
}