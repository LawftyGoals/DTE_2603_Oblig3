package com.example.oblig_3.ui.data

import android.content.Context

interface AppContainer {
    val purchaseItemsRepository: PurchaseItemRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val purchaseItemsRepository: PurchaseItemRepository by lazy {
        PurchaseItemRepository(PurchaseItemDatabase.getDatabase(context).purchaseItemDao())
    }
}