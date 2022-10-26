package com.example.f4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.data.Signup
import com.example.f4.view.CalenderActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_sign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    val TAG: String ="Register"
    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val savedToken = sp.getString(account?.email, "null")
        if (savedToken != null) {
            Token.token = savedToken
        }

        joinBtn.setOnClickListener{
            Log.d(TAG, "회원가입 버튼 클릭")

            val id = joinName.text.toString()
            val age = joinAge.text.toString()
            val height = joinHeight.text.toString()
            val weight = joinWeight.text.toString()
            val goal=joinGoal.text.toString()
            var gender =""

            val token=Token.token

            if (id.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || goal.isEmpty()){
                isExistBlank = true
            }

            if(!isExistBlank){
                (application as MasterApplication).service.signUp(token, Signup(id, "MALE", age.toDouble(), height.toDouble(), goal.toDouble(), "ON")).enqueue(object :
                    Callback<Signup> {
                    override fun onFailure(call: Call<Signup>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                        if (response.isSuccessful.not()) {
                            return
                        }
                    }
                })
                var intent= Intent(this, CalenderActivity::class.java)
                startActivity(intent)

            }
        }

    }
}