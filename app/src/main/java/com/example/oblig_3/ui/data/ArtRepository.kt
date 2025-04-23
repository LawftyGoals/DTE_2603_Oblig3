package com.example.oblig_3.ui.data

import com.example.oblig_3.network.ArtApiService
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.network.FrameSizeDto
import com.example.oblig_3.network.FrameTypeDto
import com.example.oblig_3.network.ImageDto
import com.example.oblig_3.network.ImageSizeDto

interface ArtRepository {
    //PHOTOS
    suspend fun getImages(): List<ImageDto>
    suspend fun getImageById(id: Int): ImageDto
    suspend fun getImagesByCategory(categoryId: Int): List<ImageDto>
    suspend fun getImagesByArtist(artistId: Int): List<ImageDto>
    //Categories
    suspend fun getAllCategories(): List<CategoryDto>
    suspend fun getCategoryById(id: Int): CategoryDto
    //Artists
    suspend fun getAllArtists(): List<ArtistDto>
    suspend fun getArtistById(id: Int): ArtistDto
    //ImageSizes
    suspend fun getAllImageSizes(): List<ImageSizeDto>
    //FrameType
    suspend fun getAllFrameTypes(): List<FrameTypeDto>
    //FrameSize
    suspend fun getAllFrameSizes(): List<FrameSizeDto>
}

class NetworkArtRepository(private val artApiService: ArtApiService):ArtRepository {

    //Images
    override suspend fun getImages(): List<ImageDto> {
        return artApiService.getAllImages()
    }

    override suspend fun getImageById(id: Int): ImageDto {
        return artApiService.getImageById(id)
    }

    override suspend fun getImagesByCategory(categoryId: Int): List<ImageDto> {
        return artApiService.getImagesByCategory(categoryId)
    }

    override suspend fun getImagesByArtist(artistId: Int): List<ImageDto> {
        return artApiService.getImagesByArtist(artistId)
    }

    //Categories
    override suspend fun getAllCategories(): List<CategoryDto> {
        return artApiService.getAllCategories()
    }
    override suspend fun getCategoryById(id: Int): CategoryDto {
        return artApiService.getCategoryById(id)
    }

    //Artists
    override suspend fun getAllArtists(): List<ArtistDto> {
        return artApiService.getAllArtists()
    }
    override suspend fun getArtistById(id: Int): ArtistDto {
        return artApiService.getArtistById(id)
    }

    //ImageSizes
    override suspend fun getAllImageSizes(): List<ImageSizeDto> {
        return artApiService.getAllImageSizes()
    }

    //FrameType
    override suspend fun getAllFrameTypes(): List<FrameTypeDto> {
        return artApiService.getAllFrameTypes()
    }

    //FrameSize
    override suspend fun getAllFrameSizes(): List<FrameSizeDto> {
        return artApiService.getAllFrameSizes()
    }


}