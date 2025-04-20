package com.example.oblig_3.network

import retrofit2.Retrofit


private const val BASE_URL = "http://localhost:3000/"
private const val API_KEY = "0"


private val retrofit = Retrofit.Builder().addConverterFactory(Json)

class ArtApiService {
}