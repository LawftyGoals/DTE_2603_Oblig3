package com.example.oblig_3.ui.data;

import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(purchaseItem: PurchaseItem)

    @Update
    suspend fun update(purchaseItem: PurchaseItem)

    @Delete
    suspend fun delete(purchaseItem: PurchaseItem)

    @Query("SELECT *  FROM shopping_cart WHERE id = :id")
    fun getPurchaseItemById(id: Int): Flow<PurchaseItem>

    @Query("Select * FROM shopping_cart")
    fun getAllPurchaseItems(): Flow<List<PurchaseItem>>
}
