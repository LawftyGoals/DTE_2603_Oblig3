package com.example.oblig_3.ui

import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.PurchaseItemDto

fun calculateTotalPrice(purchaseItemsList: List<PurchaseItemDto>): Float {
    var total = 0F
    purchaseItemsList.forEach {

        total += it.getCost()
    }

    return total

}