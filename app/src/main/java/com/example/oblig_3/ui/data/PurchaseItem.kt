package com.example.oblig_3.ui.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "shopping_cart")
data class PurchaseItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name="image_Id") val imageId: Int,
    @ColumnInfo(name = "image_title") val imageTitle: String,
    @ColumnInfo(name = "image_thumb_url")  val imageThumbUrl: String,
    @ColumnInfo(name = "image_url")  val imageUrl: String,
    @ColumnInfo(name = "artist_Id")  val artistId: Int,
    @ColumnInfo(name = "artist_first_name")  val artistFirstName: String,
    @ColumnInfo(name = "artist_last_name")  val artistLastName: String,
    @ColumnInfo(name = "category_id")  val categoryId: Int,
    @ColumnInfo(name = "category_name")  val categoryName: String,
    @ColumnInfo(name = "image_price")  val price: Float,
    @ColumnInfo(name = "image_size_Id") val imageSizeId: Int,
    @ColumnInfo(name = "image_size_name")  val imageSizeName: String,
    @ColumnInfo(name = "image_size_value")  val imageSizeValue: Int,
    @ColumnInfo(name = "image_size_price")  val imageSizeExtraPrice: Float,
    @ColumnInfo(name = "frame_type_id") val frameTypeId: Int,
    @ColumnInfo(name = "frame_type_name")  val frameTypeName: String,
    @ColumnInfo(name = "frame_type_value")  val frameTypeValue: String,
    @ColumnInfo(name = "frame_type_price")  val frameTypeExtraPrice: Float,
    @ColumnInfo(name = "frame_size_id") val frameSizeId: Int,
    @ColumnInfo(name = "frame_size_name")  val frameSizeName: String,
    @ColumnInfo(name = "frame_size_value")  val frameSizeValue: Int,
    @ColumnInfo(name = "frame_size_price")  val frameSizeExtraPrice: Float,
)