package com.example.oblig_3.network

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(val id: Int,
                     val title: String,
                     val imageThumbUrl: String,
                     val imageUrl: String,
                     val artistId: Int,
                     val categoryId: Int,
                     val price: Float)

@Serializable
data class CategoryDto(val id: Int, val name: String)

@Serializable
data class ArtistDto(val id: Int, val firstName: String, val lastName: String)

@Serializable
data class FrameType(val id: Int, val name: String, val color: String, val extraPrice: Float)

@Serializable
data class FrameSize(val id: Int, val name: String, val size: Int, val extraPrice: Float)

@Serializable
data class PhotoSize(val id: Int, val name: String, val size: Int, val extraPrice: Float)