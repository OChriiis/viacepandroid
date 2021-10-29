package com.example.retrofitviacep

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class RetrofitFactory {

    val BASE_URL = "https://10.0.2.2:8080/api/"
    val URL = "https://viacep.com.br/ws/"

    val retrofitFactory = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitService() : RetrofitService {
        return  retrofitFactory.create(RetrofitService::class.java)
    }
}