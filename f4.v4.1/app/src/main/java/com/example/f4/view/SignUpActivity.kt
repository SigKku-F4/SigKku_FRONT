package com.example.f4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.data.Signup
import com.example.f4.view.CalenderActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_sign.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    val TAG: String ="Register"
    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        //Token.token 사용하는 모든 곳에 붙여넣기 (header에 jwt token을 넣기 위함)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val savedToken = sp.getString(account?.email, "null")
        if (savedToken != null) {
            Token.token = savedToken
        }

        joinBtn.setOnClickListener{
            Log.d(TAG, "회원가입 버튼 클릭")

            //사용자가 입력한 정보들 받아온 변수
            val id = joinName.text.toString()
            val age = joinAge.text.toString()
            val height = joinHeight.text.toString()
            val weight = joinWeight.text.toString()
            val goal=joinGoal.text.toString()
            var gender =""

            val token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2h5dW41MzM1QGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjMwMzg1NDYsImV4cCI6MTY2NTYzMDU0Nn0.dUaS2g_eKDKdlHSKT0gfev3jaLqJo57fGTgu1lKV0eI"


            //빈 칸일 때
            if (id.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || goal.isEmpty()){
                isExistBlank = true
            }
//            else{
//                if(!weight.isEmpty()&&!goal.isEmpty()){
//                    val weightInt = weight.toInt()
//                    val goalInt = goal.toInt()
//
//                    val result = weightInt - goalInt
//
//                    if(weightInt >= goalInt){
//                        joinResultText.setText("목표 체중까지 -"+result+"kg")
//
//                    }
//                    else{
//                        joinResultText.setText("목표 체중까지 +"+(-result)+"kg")
//                    }
//                }
//            }

            //정보 다 입력 받아야만 가능
            if(!isExistBlank){
                (application as MasterApplication).service.signUp(token, Signup(id, "MALE", age.toDouble(), height.toDouble(), goal.toDouble(), "ON")).enqueue(object :
                    Callback<Signup> {
                    override fun onFailure(call: Call<Signup>, t: Throwable) {
                        Log.d("TEST fail : ", "signup fail")
                    }

                    override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                        if (response.isSuccessful.not()) {
                            Log.d("Test fail : ", "signup response")
                            return
                        }
                        Log.d("TEST response : ", "성공~")
                    }
                })
                //저장 누르면 넘어가는 버튼
                var intent= Intent(this, CalenderActivity::class.java)
                startActivity(intent)

            }
        }

    }
}