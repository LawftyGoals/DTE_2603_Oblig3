package com.example.oblig_3.ui

import com.example.oblig_3.ui.data.PurchaseItem

class HelperFunctions {



}

fun calculateTotalPrice(purchaseItemsList: List<PurchaseItem>): Float {
    var total = 0F
    purchaseItemsList.forEach {

        total += it.getCost()
        /*
        total += (it.photo?.price ?: 0F)
        total += (it.frameType.extraPrice)
        total += (it.frameSize.extraPrice)
        total += (it.size.extraPrice)*/
    }

    return total

}