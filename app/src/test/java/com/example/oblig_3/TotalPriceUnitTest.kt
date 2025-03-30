package com.example.oblig_3

import com.example.oblig_3.ui.calculateTotalPrice
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.FrameSize
import com.example.oblig_3.ui.data.FrameType
import com.example.oblig_3.ui.data.PhotoSize
import com.example.oblig_3.ui.data.PurchaseItem
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TotalPriceUnitTest {



    val photos = DataSource.photos

    val photoOne = photos[0] // price 100f
    val photoTwo = photos[1] // price 150f
    val photoThree = photos[4] //price 10f

    val smallPhoto = PhotoSize.SMALL // 0f
    val mediumPhoto = PhotoSize.MEDIUM // 130f
    val largePhoto = PhotoSize.LARGE // 230f

    val woodFrame = FrameType.WOOD // 0f
    val metalFrame = FrameType.METAL // 100f
    val plasticFrame = FrameType.PLASTIC // 30f

    val smallFrame = FrameSize.SMALL // 0f
    val mediumFrame = FrameSize.MEDIUM // 25f
    val largeFrame = FrameSize.LARGE // 45f

    val purchaseItemOne = PurchaseItem(photoOne, smallPhoto, woodFrame, smallFrame) // 100f + 0f + 0f +0f = 100f
    val purchaseItemTwo = PurchaseItem(photoTwo, largePhoto, metalFrame, largeFrame) // 150f + 230f + 100f + 45f = 525f
    val purchaseItemThree = PurchaseItem(photoThree, mediumPhoto, plasticFrame, mediumFrame) // 10f + 130f + 30f + 25f = 195f
    //total = 820


    @Test
    fun total_cost_multiple_photos(){

        val purchaseItems = listOf(purchaseItemOne, purchaseItemTwo, purchaseItemThree)

        assertEquals(820f, calculateTotalPrice(purchaseItems))
        assertEquals(625f, calculateTotalPrice(listOf(purchaseItemOne, purchaseItemTwo)))
        assertEquals(0f, calculateTotalPrice(listOf<PurchaseItem>()))

    }

    @Test
    fun total_cost_single_photo(){

        val purchaseItems = listOf(purchaseItemTwo)

        assertEquals(525f, calculateTotalPrice(purchaseItems))

    }

    @Test
    fun total_cost_no_photos(){


        assertEquals(0f, calculateTotalPrice(listOf<PurchaseItem>()))

    }
}