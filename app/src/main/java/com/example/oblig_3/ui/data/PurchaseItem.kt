package com.example.oblig_3.ui.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "shopping_cart")
data class PurchaseItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name="photo_Id")val photoId: Int,
    @ColumnInfo(name = "photo_size") val photoSize: PhotoSize,
    @ColumnInfo(name = "frame_size") val frameType: FrameType,
    @ColumnInfo(name = "frame_type") val frameSize: FrameSize
)