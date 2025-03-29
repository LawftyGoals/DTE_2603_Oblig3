package com.example.oblig_3.ui

import com.example.oblig_3.ui.data.PurchaseItem

fun calculateTotalPrice(purchaseItemsList: List<PurchaseItem>): Float {
    var total = 0F
    purchaseItemsList.forEach {

        total += it.getCost()
    }

    return total

}