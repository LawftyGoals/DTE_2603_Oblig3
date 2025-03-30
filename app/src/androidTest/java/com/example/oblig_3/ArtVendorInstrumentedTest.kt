package com.example.oblig_3

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oblig_3.ui.theme.Oblig_3Theme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ArtVendorInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val artist = "Kunstner"
    private val category = "Kategori"
    private val artistFirstName = "Jane"
    private val sport = "SPORT"
    private val food = "FOOD"

    private val foodTag = "photo-2"

    private val metal = "METAL"
    private val medium = "Medium"

    private val frameSizeTwenty = "20"
    private val zero = "0"

    private val addToCart = "Legg i handlekurv"
    private val home = "Hjem"
    private val numberOfPhotosChosen = "Antall bilder valgt:"

    private val toPayment = "Til betaling"
    private val delete = "Delete"


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.oblig_3", appContext.packageName)
    }

    @Test
    fun get_price_of_one_artist_painting() {
        composeTestRule.setContent {
            Oblig_3Theme {
                ArtVendorApp()
            }
        }

        composeTestRule.onNodeWithText(artist).performClick()
        composeTestRule.onNodeWithText(artistFirstName).performClick()
        composeTestRule.onNodeWithText(sport).performClick()
        composeTestRule.onNodeWithTag(metal).performClick()
        composeTestRule.onNodeWithText(medium).performClick()
        composeTestRule.onNodeWithText(frameSizeTwenty).performClick()
        composeTestRule.onNodeWithText(addToCart).performClick()
        composeTestRule.onNodeWithText(toPayment).performClick()
        composeTestRule.onNodeWithText("395.00").assertTextEquals("395.00")

    }


    @Test
    fun get_abort_number_from_category_painting() {
        composeTestRule.setContent {
            Oblig_3Theme {
                ArtVendorApp()
            }
        }

        composeTestRule.onNodeWithText(category).performClick()
        composeTestRule.onNodeWithText(food).performClick()
        composeTestRule.onNodeWithTag(foodTag).performClick()
        composeTestRule.onNodeWithText(home).performClick()
        composeTestRule.onNodeWithText(numberOfPhotosChosen, substring = true).assertTextEquals("$numberOfPhotosChosen $zero")

    }


    @Test
    fun get_number_after_item_delete() {
        composeTestRule.setContent {
            Oblig_3Theme {
                ArtVendorApp()
            }
        }

        composeTestRule.onNodeWithText(category).performClick()
        composeTestRule.onNodeWithText(food).performClick()
        composeTestRule.onNodeWithTag(foodTag).performClick()
        composeTestRule.onNodeWithText(addToCart).performClick()
        composeTestRule.onNodeWithTag(delete).performClick()
        composeTestRule.onNodeWithText(numberOfPhotosChosen, substring = true).assertTextEquals("$numberOfPhotosChosen $zero")

    }




}