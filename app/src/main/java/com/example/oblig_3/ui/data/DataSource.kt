package com.example.oblig_3.ui.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.oblig_3.R

object DataSource {
    val artists = listOf(
        Artist(1L, "Jack", "Jackson"),
        Artist(2L, "Jane", "Janedaughter"),
        Artist(3L, "Zack", "Zackson"),
        Artist(4L, "Dame", "Damedaughter"),
        Artist(5L, "Cleatus", "Cleatusson")
    )

    val photos = listOf(
        Photo(1L, "My first attempt", R.drawable.image1, artists[0], Category.NATURE, 100f ),
        Photo(2L, "My first attempt", R.drawable.image2, artists[1], Category.FOOD, 150f ),
        Photo(3L, "My first attempt", R.drawable.image3, artists[2], Category.SPORT, 180f ),
        Photo(4L, "My first attempt", R.drawable.image4, artists[3], Category.NATURE, 190f ),
        Photo(5L, "My first attempt", R.drawable.image5, artists[0], Category.FOOD, 10f ),
        Photo(8L, "My first attempt", R.drawable.image6, artists[1], Category.SPORT, 120f ),
        Photo(9L, "My first attempt", R.drawable.image7, artists[2], Category.NATURE, 130f ),
        Photo(10L, "My first attempt", R.drawable.image8, artists[3], Category.FOOD, 140f ),
        Photo(11L, "My first attempt", R.drawable.image9, artists[4], Category.SPORT, 150f ),
        Photo(12L, "My first attempt", R.drawable.image10, artists[4], Category.NATURE, 160f )

    )


    val categories = Category.entries.toList()

    val frames = FrameType.entries.toList()

    val photoSizes = PhotoSize.entries.toList()

    val frameSizes = FrameSize.entries.toList()

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

enum class Category {
    NATURE(),
    FOOD(),
    SPORT()
}

enum class Filters {
    ARTIST(),
    CATEGORY()
}

enum class FrameType(val extraPrice: Float, val color: Color = Color.Yellow, @StringRes val title: Int) {
    WOOD(0f, color = Color.Yellow, title=R.string.wood),
    METAL(100f, color = Color.Blue, title=R.string.metal),
    PLASTIC(30f, color = Color.Green, title=R.string.plastic)
}

enum class PhotoSize(val extraPrice: Float, val size: Int = 170, @StringRes val title: Int) {
    SMALL(0f, size=170, title = R.string.small),
    MEDIUM(130f, size=200, title = R.string.medium),
    LARGE(230f, size=250, title = R.string.large)
}

enum class FrameSize(val extraPrice: Float, val size: Int = 10){
    SMALL(0f, size=10),
    MEDIUM(25f, size=15),
    LARGE(45f, size=20)

}

val testArtist = Artist(-1L)
val testPhoto = Photo(id= -1L,imageResId=R.drawable.ic_launcher_background, artist=testArtist, category=Category.NATURE)

class PurchaseItem(var photo: Photo = testPhoto, var size: PhotoSize = PhotoSize.SMALL, val frameType: FrameType = FrameType.WOOD, val frameSize: FrameSize = FrameSize.SMALL){
    fun getCost(): Float {
        val tempPhoto = photo
        return (tempPhoto.price
            + size.extraPrice + frameType.extraPrice + frameSize.extraPrice)
    }
}