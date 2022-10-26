package com.example.f4.interfaces

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var instance: Retrofit? = null
    private const val CONNECT_TIMEOUT_SEC = 20000L

    fun getInstance() : Retrofit {
        if(instance == null){

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build()

            instance = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return instance!!
    }
}