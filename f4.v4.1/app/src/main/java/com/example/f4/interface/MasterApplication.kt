package com.example.f4.`interface`

import android.app.Application
import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MasterApplication : Application() {

    lateinit var service: RetrofitService
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        createRetrofit() // 사용자 로그인 여부에 의해 retrofit을 만들어냄 (토큰 있, 없)
    }

    fun createRetrofit() {

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        //Retrofit 초기화
        retrofit = Retrofit.Builder()
            .baseUrl("http://sikku.kro.kr:8080/") //BaseURL
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        service = retrofit.create(RetrofitService::class.java)

    }

    // 로그인 확인
    // sharedPreference에 토큰 값이 있으면 로그인 된 것으로 간주, 없으면 로그인 안된 것으로 간주
    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE) // sp에서 값을 가져옴
        val token = sp.getString("login_token", "null")
        Log.d("Check User Token: ", " " + token)
        return token != "null"  // 토큰이 null이 아니면 true, null이면 false 반환
    }

    // 토큰 값을 내보냄
    // 로그인이 안된 경우 null을 내보내야 하므로 nullable
    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val token = sp.getString("login_token", "null")
        return if (token == "null") null    // 토큰이 null이면 null
        else token  // null이 아니면 토큰 값 내보냄
    }

}

object Token {
    var token: String =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2h5dW41MzM1QGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjUwMTkwMjksImV4cCI6MTY2NzYxMTAyOX0.FgvYpFOnLLt9j3Iyv5s3tvhuTMRwiUUXhMH9BlDnqlQ"
}
