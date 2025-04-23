package com.example.oblig_3.ui

import com.example.oblig_3.R
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.image.PurchaseItemDto

fun calculateTotalPrice(purchaseItemsList: List<PurchaseItem>): Float {
    var total = 0F
    purchaseItemsList.forEach {

        total += it.price + it.frameSizeExtraPrice + it.frameTypeExtraPrice + it.frameTypeExtraPrice
    }

    return total

}

fun convertValueToStringResource(value: String): Int {
    return when (value) {
        "Wood" -> R.string.wood
        "Metal" -> R.string.metal
        "Plastic" -> R.string.plastic
        "Small" -> R.string.small
        "Medium" -> R.string.medium
        "Large" -> R.string.large
        else -> R.string.placeholder
    }
}