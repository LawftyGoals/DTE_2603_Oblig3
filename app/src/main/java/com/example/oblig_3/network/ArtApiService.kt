package com.example.oblig_3.network

import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query



interface ArtApiService {
    //Images
    // *** GET
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/photos")
    suspend fun getAllImages(): List<ImageDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos/{id}")
    suspend fun getImageById(@Path("id") id: Int): ImageDto

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos")
    suspend fun getImagesByCategory(@Query("categoryId") categoryId: Int): List<ImageDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/photos")
    suspend fun getImagesByArtist(@Query("artistId") artistId: Int): List<ImageDto>


    //Categories
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/categories")
    suspend fun getAllCategories(): List<CategoryDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): CategoryDto


    //Artists
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/artists")
    suspend fun getAllArtists(): List<ArtistDto>

    @Headers(
        "Content-Type: application/json", "User-Agent: android", "Accept: application/json"
    )
    @GET("/artists/{id}")
    suspend fun getArtistById(@Path("id") id: Int): ArtistDto


    //ImageSizes
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/photosizes")
    suspend fun getAllImageSizes(): List<ImageSizeDto>


    //FrameSizes
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/framesizes")
    suspend fun getAllFrameSizes(): List<FrameSizeDto>


    //FrameType
    @Headers("Content-Type: application/json", "User-Agent: android", "Accept: application/json")
    @GET("/frametypes")
    suspend fun getAllFrameTypes(): List<FrameTypeDto>

}

sealed interface NetworkState {
    data class Success(val info: String) : NetworkState
    data class Error(val error: String) : NetworkState
    object Loading : NetworkState
}


fun getHttpErrorMessage(e: HttpException): String {
    val code = e.code()
    val msg = e.localizedMessage
    var message = ""
    when(code) {
        401 -> {
            message = "Manglende eller feil Authorization-header ($code): $msg"
        }
        403 -> {
            message = "Ingen tilgang ($code): $msg"
        }
        404 -> {
            message = "Ressurs ikke funnet ($code): $msg"
        }
        409 -> {
            message = "Konflikt (muligens duplisert nøkkelverdi) ($code): $msg"
        }
        501 -> {
            message = "Serverfeil: Ressursen er ikke implementert ($code): $msg"
        }
        502 -> {
            message = "Serverfeil: Ukjent serverfeil eller nettverksfeil ($code): $msg"
        }
        else -> {
            message = "En annen HTTP-feil oppsto ($code): $msg"
        }
    }
    return message
}
