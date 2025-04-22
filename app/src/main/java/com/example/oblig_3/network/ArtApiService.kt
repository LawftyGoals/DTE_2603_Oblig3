package com.example.oblig_3.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "http://10.0.2.2:3000/"
private const val API_KEY = "0"


private val retrofit = Retrofit.Builder().addConverterFactory(
    Json.asConverterFactory("application/json".toMediaType())
).baseUrl(BASE_URL).build()

interface ArtApiService {
    //Photos
    // *** GET
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/photos")
    suspend fun getAllPhotos(): List<PhotoDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): PhotoDto

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos")
    suspend fun getPhotosByCategory(@Query("category_id") category_id: Int): List<PhotoDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos")
    suspend fun getPhotosByArtist(@Query("category_id") artist_id: Int): List<PhotoDto>


    //Categories
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/categories")
    suspend fun getAllCategories(): List<CategoryDto>


    //Artists
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/artists")
    suspend fun getAllArtists(): List<ArtistDto>


    //PhotoSizes
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/photosizes")
    suspend fun getAllPhotoSizes(): List<PhotoSize>


    //FrameSizes
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/framesizes")
    suspend fun getAllFrameSizes(): List<FrameSize>


    //FrameType
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/frametypes")
    suspend fun getAllFrameTypes(): List<FrameType>

}

object ArtApi {
    val retrofitService: ArtApiService by lazy { retrofit.create(ArtApiService::class.java) }
}

sealed interface NetworkState {
    data class Success(val info: String) : NetworkState
    data class Error(val error: String) : NetworkState
    object Loading : NetworkState
}