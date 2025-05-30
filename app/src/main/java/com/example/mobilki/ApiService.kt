package com.example.mobilki

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GenderApiService {
    @GET("/")
    suspend fun getGenderByName(@Query("name") name: String): Response<GenderApiResponse>
}

object ApiClient {
    private const val BASE_URL = "https://api.genderize.io"
    
    val genderApiService: GenderApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenderApiService::class.java)
    }
} 