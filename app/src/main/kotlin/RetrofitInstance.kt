// RetrofitInstance.kt
package com.example.housemanager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //private const val BASE_URL = "https://10.0.2.2:8000"
    //private const val BASE_URL = "https://10.0.2.2:5000"
    private const val BASE_URL = "https://hm.jftt.kr"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
