package com.example.f4.`interface`

import android.content.Context
import com.example.f4.data.Login
import com.example.f4.data.*
import com.example.f4.data.Calendar
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

interface RetrofitService {

//----------------------------------------------------------소현
    //로그인
    @POST("app/accounts/auth")
    fun googleLogin(
        @Body id_token: Id_token //to server
    ): Call<Login>

    //음식 기록
    @POST("calendar/{date}")
    fun record(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date: LocalDate,
        @Body record: Record //to server
    ): Call<ResponseBody>

    //음식 수정
    @PATCH("calendar/{date}")
    fun recordChange(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date: LocalDate,
        @Body changeRecord: Change_record //to server
    ): Call<ResponseBody>

    //그룹 설정 - 그룹장(L)
    @GET("groups/admin/settings/{groupId}")
    fun groupSettingL(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long
    ): Call<Group_setting_l>

    @PATCH("groups/admin/settings/{groupId}")
    fun groupSetChangeL(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long,
        @Body groupSetChangeL: Group_set_change_l
    ): Call<Group_set_change_l>

    @DELETE("groups/admin/{groupId}")
    fun groupSetDeleteL(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long
    ): Call<Boolean>

    //그룹 설정 - 그룹원(M)
    @GET("groups/user-group/{groupId}")
    fun groupSettingM(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long
    ): Call<Group_setting_m>

    @PATCH("groups/user-group/{groupId}")
    fun groupSetChangeM(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long,
        @Body groupSetChangeM: Group_setting_m
    ): Call<Group_setting_m>

    @DELETE("groups/user-group/{groupId}")
    fun groupSetDeleteM(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupid: Long
    ): Call<Boolean>

    //설정 - 정보 수정
    @GET("user")
    fun getInfo(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<Change_info>

    @PATCH("user")
    fun changeInfo(
        @Header("X-AUTH-TOKEN") token: String,
        @Body changeInfo: Change_info //to server
    ): Call<Change_info>

//-------------------------------------------------------------
//----------------------------------------------------------유림


    @GET("calendar/{date}")
    fun diet(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date: LocalDate
    ): Call<Diet>

    @GET("groups/user/calendar/{userId}")
    fun groupCalendar(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userId") userId : Long,
        @Query("date") date : String
    ): Call <ArrayList<Calendar>>

    @GET("groups/user/calendar/detail/{userId}")
    fun groupDiet(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userId") userId : Long,
        @Query("groupId") groupId: Long,
        @Query("date") date: LocalDate
    ): Call<Diet>


    //chuga
    @GET("user/{date}")
    fun calender(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date : String
    ): Call <ArrayList<Calendar>>

    @POST("groups")
    fun groupCreate(
        @Header("X-AUTH-TOKEN") token: String,
        @Body groupInfo: Groupinfo
    ): Call<Groupid>

    @POST("groups/user")
    fun groupSign(
        @Header("X-AUTH-TOKEN") token: String,
        @Body groupSign: Groupsign
    ): Call<Groupid>



    @POST("signup")
    fun signUp(
        @Header("X-AUTH-TOKEN") token: String,
        @Body signup: Signup //to server
    ): Call<Signup>

//-------------------------------------------------------------
//----------------------------------------------------------비비
@GET("foods/{foodName}")
fun addFoods(
    @Header("X-AUTH-TOKEN") token: String,
    @Path("foodName") foodName: String,
    @Query("page") page: Int,
    @Query("size") size: Int
): Call<Addfood>

    @GET("foods")
    fun detailFoods(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("foodListId") foodListId: Long
    ): Call<Addfooddetail>

    @GET("foods/recommend/my/{date}")
    fun recommendFoodsMy(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Recommendedfoodmy>

    @GET("foods/recommend/{date}")
    fun recommendFoods(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("date") date: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Recommendedfood>

    @GET("foods/recommend")
    fun recommendFoodsSearch(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("date") date: String,
        @Query("foodName") foodName: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Call<Recommendedsearch>

    @GET("groups")
    fun mainGroups(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<ArrayList<Maingroup>>

    @GET("groups/{groupId}")
    fun myGroups(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("groupId") groupId: Long
    ): Call<Mygroup>

}

//-------------------------------------------------------------
