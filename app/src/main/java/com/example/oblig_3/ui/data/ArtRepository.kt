package com.example.oblig_3.ui.data

import com.example.oblig_3.network.ArtApiService
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.network.FrameSizeDto
import com.example.oblig_3.network.FrameTypeDto
import com.example.oblig_3.network.PhotoDto
import com.example.oblig_3.network.PhotoSizeDto

interface ArtRepository {
    //PHOTOS
    suspend fun getPhotos(): List<PhotoDto>
    suspend fun getPhotoById(id: Int): PhotoDto
    suspend fun getPhotosByCategory(categoryId: Int): List<PhotoDto>
    suspend fun getPhotosByArtist(artistId: Int): List<PhotoDto>
    //Categories
    suspend fun getAllCategories(): List<CategoryDto>
    suspend fun getCategoryById(id: Int): CategoryDto
    //Artists
    suspend fun getAllArtists(): List<ArtistDto>
    suspend fun getArtistById(id: Int): ArtistDto
    //PhotoSizes
    suspend fun getAllPhotoSizes(): List<PhotoSizeDto>
    //FrameType
    suspend fun getAllFrameTypes(): List<FrameTypeDto>
    //FrameSize
    suspend fun getAllFrameSizes(): List<FrameSizeDto>
}

class NetworkArtRepository(private val artApiService: ArtApiService):ArtRepository {

    //Photos
    override suspend fun getPhotos(): List<PhotoDto> {
        return artApiService.getAllPhotos()
    }

    override suspend fun getPhotoById(id: Int): PhotoDto {
        return artApiService.getPhotoById(id)
    }

    override suspend fun getPhotosByCategory(categoryId: Int): List<PhotoDto> {
        return artApiService.getPhotosByCategory(categoryId)
    }

    override suspend fun getPhotosByArtist(artistId: Int): List<PhotoDto> {
        return artApiService.getPhotosByArtist(artistId)
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

    //PhotoSizes
    override suspend fun getAllPhotoSizes(): List<PhotoSizeDto> {
        return artApiService.getAllPhotoSizes()
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