package com.example.f4.data

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.time.LocalDate
import java.io.Serializable


//노션 API 문서 참고해서 API 문서랑 또옥같이 적으면 됨 (class 이름 형식은 Aaabbb로 통일하자)
//헷갈릴까봐 정리함


//----------------------------------------------------------소현

//로그인
data class Login ( //from server
        val jwtToken: String,
        val googleConfirm: String,
        val redirectUrl: String
)
data class Id_token ( //to server
        val id_token: String
)

//음식 기록 & 수정
data class Record( //to server
        val mealType: String,
        val description: String,
        val foods: List<Foods>,
        val images: List<Images>
)
data class Change_record(
        val mealId: Long,
        val mealType: String,
        val description: String,
        val foods: List<FoodsC>,
        val images: List<Images>
)
data class Images(
        val image: String
)
data class Foods(
        var foodListId: Long,
        var amount: Double
)
data class FoodsC(
        var foodId: Long,
        var foodListId: Long,
        var amount: Double
)

//그룹 설정 - 그룹장(L)
data class Group_setting_l( //from server
        val groupName: String,
        val profile: String,
        val description: String,
        val groupCode: String
)
data class Group_set_change_l(
        val groupName: String,
        val profile: String,
        val description: String,
        val maxSize: Int
)

//그룹 설정 - 그룹원(M)
data class Group_setting_m( //from server
        val profile: String,
        val calendarSetting: Boolean
)

//설정 - 정보 수정
data class Change_info( //from server
        val nickname: String,
        val gender: String,
        val age: Int,
        val height: Int,
        val weight: Int
)

//-------------------------------------------------------------
//----------------------------------------------------------유림


data class Nutrient(
        val phosphorus : Double,
        val calorie : Double,
        val carbohydrate : Double,
        val dietaryFiber : Double,
        val fat : Double,
        val protein : Double,
        val moisture : Double,
        val vitaminB12 :Double,
        val folicAcid : Double,
        val calcium : Double,
        val sodium : Double,
        val potassium : Double,
        val magnesium: Double

):Serializable

data class Diet(
        val date :String,
        val stamp : String,
        val totalKcal : Double,
        val eatNutrient : Nutrient,
        val needNutrient: Nutrient,
        val meals : ArrayList<Meal>
):Serializable

data class Meal(
        val mealId :Long,
        val mealType : String,
        var foods: ArrayList<Food>,
        val imgs :ArrayList<String>
):Serializable


data class Food(
        val name: String,
        val foodListId: Long,
        val foodId: Long,
        val amount: Double,
        val nutrient: Nutrient
):Serializable



//chuga

data class Groupid( //from server
        @SerializedName("groupId") val groupId: Long
)

data class Groupsign(
        @SerializedName("groupCode")  val groupCode : String,
        @SerializedName("profile")val profile: String
)

data class Groupinfo( //to server
        @SerializedName("groupName")val groupName: String,
        @SerializedName("description")val description: String,
        @SerializedName("maxSize")val maxSize: Int,
        @SerializedName("profile")val profile: String
)



data class Calendar( //from sever
        @SerializedName("calender")
        val dateId: Long,
        val stamp: String,
        val dateTime: String
)


data class Signup( //to server
        @SerializedName("nickname")val nickname: String,
        @SerializedName("gender")val gender: String,
        @SerializedName("age")val age: Double,
        @SerializedName("height")val height: Double,
        @SerializedName("weight")val weight: Double,
        @SerializedName("notification")val notification: String
)


//-------------------------------------------------------------
//----------------------------------------------------------비비
data class Addfood( //from server
        val foodList: List<Foodlist>,
        val totalElements: Long,
        val pageNumber: Int
)

data class Foodlist(
        val foodListId: Long,
        val foodName: String,
        val category: String,
        val kcal: Double,
        val oneSupplyAmount: Double
)

data class Addfooddetail( //from server
        val foodName: String,
        val foodId: Long,
        val category: String,
        val oneSupplyAmount: Double,
        val nutrient: Nutrientlist
)

data class Nutrientlist(
        val calorie: Double,
        val carbohydrate: Double,
        val protein: Double,
        val fat: Double,
        val phosphorus: Double, //(인)
        val dietaryFiber: Double, //(식이섬유)
        val moisutre: Double, //(수분)
        val vitaminB12: Double,
        val folicAcid: Double, //(엽산)
        val calcium: Double,
        val sodium: Double, //(나트륨)
        val potassium: Double, //(칼륨)
        val magnesium: Double
)

data class Foodlists(
        val foodListId: Long,
        val foodName: String,
        val category: String,
        val oneSupplyAmount: Double,
        val nutrient: Nutrientlist
)

data class Pagemetalist(
        val totalElements: Long,
        val nowPage: Int
)

data class Recommendedfoodmy( //from server
        val recommendComments: String,
        val foodLists: List<Foodlists>,
        val pageMeta: Pagemetalist
)

data class Recommendedfood( //from server
        val recommendComments: String,
        val foodLists: List<Foodlists>,
        val pageMeta: Pagemetalist
)

data class Recommendedsearch( //from server
        val recommendComments: String,
        val foodLists: List<Foodlists>,
        val pageMeta: Pagemetalist
)

data class Maingroup (
        val groupId: Long,
        val groupName: String,
        val groupMaxSize: Int,
        val groupCurrentSize: Int,
        val userNickname: List<String>
)

data class Mygroup( //from server
        val groupId: Long,
        val groupName: String,
        val description: String,
        val isOwner: Boolean,
        val userinfo: List<Userinfo>
)
data class Userinfo(
        val userId: Long,
        val profile: String,
        val calendarSetting: Boolean,
        val userNickName: String,
        val greenStamp: Int,
        val yellowStamp: Int,
        val redStamp: Int
)

//-------------------------------------------------------------
