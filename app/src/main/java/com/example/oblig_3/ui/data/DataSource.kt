package com.example.oblig_3.ui.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.oblig_3.R

class DataSource {
    val artists = listOf(
        Artist(1L, "Jack", "Jackson"),
        Artist(2L, "Jane", "Janedaughter"),
        Artist(3L, "Zack", "Zackson"),
        Artist(4L, "Dame", "Damedaughter")
    )

    val photos = listOf(
        Photo(1L, "My first attempt", R.drawable.image1, artists[0], Category.NATURE, 100f ),
        Photo(1L, "My first attempt", R.drawable.image2, artists[1], Category.FOOD, 150f ),
        Photo(1L, "My first attempt", R.drawable.image3, artists[2], Category.SPORT, 180f ),
        Photo(1L, "My first attempt", R.drawable.image4, artists[3], Category.NATURE, 190f ),
        Photo(1L, "My first attempt", R.drawable.image5, artists[0], Category.FOOD, 10f ),
        Photo(1L, "My first attempt", R.drawable.image6, artists[1], Category.SPORT, 120f ),
        Photo(1L, "My first attempt", R.drawable.image7, artists[2], Category.NATURE, 130f ),
        Photo(1L, "My first attempt", R.drawable.image8, artists[3], Category.FOOD, 140f ),
        Photo(1L, "My first attempt", R.drawable.image9, artists[0], Category.SPORT, 150f ),
        Photo(1L, "My first attempt", R.drawable.image10, artists[1], Category.NATURE, 160f )

    )

    val photoCategories = Category.entries.toList()


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