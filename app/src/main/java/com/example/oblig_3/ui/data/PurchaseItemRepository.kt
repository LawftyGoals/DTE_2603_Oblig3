package com.example.oblig_3.ui.data


import kotlinx.coroutines.flow.Flow

class PurchaseItemRepository(private val purchaseItemDao: PurchaseItemDao) {
    //FLOW FUNCTIONS
    fun getAllPurchaseItemsStream(): Flow<List<PurchaseItem>> = purchaseItemDao
        .getAllPurchaseItems()
    fun getPurchaseItemStreamById(id: Int): Flow<PurchaseItem> = purchaseItemDao
        .getPurchaseItemById(id)

    //ASYNC FUNCTIONS
    suspend fun insertPurchaseItem(purchaseItem: PurchaseItem) = purchaseItemDao.insert(purchaseItem)
    suspend fun deleteItem(purchaseItem: PurchaseItem) = purchaseItemDao.delete(purchaseItem)
    suspend fun updateItem(purchaseItem: PurchaseItem) = purchaseItemDao.update(purchaseItem)
}