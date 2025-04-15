package com.example.oblig_3.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PurchaseItem::class], version = 1)
abstract class PurchaseItemDatabase : RoomDatabase() {
    abstract fun purchaseItemDao(): PurchaseItemDao

    companion object {
        @Volatile
        private var Instance: PurchaseItemDatabase? = null
        fun getDatabase(context: Context): PurchaseItemDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                                context, PurchaseItemDatabase::class.java,
                                "shopping_cart_database"
                            ).fallbackToDestructiveMigration(true).build().also { Instance = it }
            }
        }
    }
}