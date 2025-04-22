package com.example.oblig_3.ui.data

import android.content.Context
import com.example.oblig_3.network.ArtApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val purchaseItemsRepository: PurchaseItemRepository
    val artRepository: ArtRepository
}

class AppDataContainer(private val context: Context): AppContainer {

    //SHOPPING CART
    override val purchaseItemsRepository: PurchaseItemRepository by lazy {
        PurchaseItemRepository(PurchaseItemDatabase.getDatabase(context).purchaseItemDao())
    }

    //RETROFIT ART
    private val BASE_URL = "http://10.0.2.2:5000/"
    private val API_KEY = "0"

    private val retrofit = Retrofit.Builder().addConverterFactory(
        Json.asConverterFactory("application/json".toMediaType())
    ).baseUrl(BASE_URL).build()

    val retrofitService: ArtApiService by lazy { retrofit.create(ArtApiService::class.java) }

    override val artRepository: ArtRepository by lazy  {
        NetworkArtRepository(retrofitService)
    }

}