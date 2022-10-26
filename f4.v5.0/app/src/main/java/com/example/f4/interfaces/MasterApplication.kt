package com.example.f4.interfaces

import android.app.Application
import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MasterApplication : Application() {

    lateinit var service: RetrofitService
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        createRetrofit()
    }

    fun createRetrofit() {

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        retrofit = Retrofit.Builder()
            .baseUrl("") //BaseURL
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        service = retrofit.create(RetrofitService::class.java)

    }

    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val token = sp.getString("login_token", "null")
        Log.d("Check User Token: ", " " + token)
        return token != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val token = sp.getString("login_token", "null")
        return if (token == "null") null
        else token
    }

}

object Token {
    var token: String = ""}
