package com.example.f4.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.f4.R
import com.example.f4.SignUpActivity
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.RetrofitService
import com.example.f4.`interface`.Token
import com.example.f4.data.Id_token
import com.example.f4.data.Login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep


class LoginActivity : AppCompatActivity() {

    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //구글 로그인 시 사용자의 데이터 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //이미 로그인이 되어 있는 지 확인
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val email: String? = account?.email
        val id_token: String? = account?.idToken

        if (account == null) {
            Log.e("Google account", "로그인 되지 않은 상태")
        } else {
            Log.e("Google account", "로그인 완료된 상태")
            login(email.toString(), id_token.toString())
        }

        //로그인 버튼 사이즈(길게)
        val google_login_button = findViewById<SignInButton>(R.id.googleLoginBtn)
        google_login_button.setSize(SignInButton.SIZE_WIDE)

        //사용자 객체 가져오기
        GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        //구글 로그인 버튼
        google_login_button.setOnClickListener {
            //로그인
            var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
            GoogleSignResultLauncher.launch(signIntent)
        }

//        val sample_logout_button = findViewById<TextView>(R.id.sampleLogout)
//        sample_logout_button.setOnClickListener(logoutButtonListener())

    }

//    inner class logoutButtonListener:View.OnClickListener{
//        override fun onClick(v: View?) {
//            when(v?.id){
//                R.id.sampleLogout -> {
//                    logout()
//                }
//            }
//        }
//    }

//    private fun logout() {
//        val mGoogleSignInClient
//        mGoogleSignInClient.signOut()
//            .addOnCompleteListener(this) {
//                // 로그아웃 성공시 실행
//                // 로그아웃 이후의 이벤트들(토스트 메세지, 화면 종료)을 여기서 수행하면 됨
//                Toast.makeText(this@MainActivity, "로그아웃", Toast.LENGTH_SHORT).show()
//            }
//    }

    //사용자에 대한 정보 가져오기
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var idToken = account?.idToken.toString()
            //val name = account?.displayName.toString()
            //var googletokenAuth = account?.serverAuthCode.toString()

            login(email, idToken)

        } catch (e: ApiException){
            Log.e("Google account","signInResult:failed Code = " + e.statusCode)
        }
    }

    //User의 token을 SharedPreferences에 저장
    fun saveUserToken(token: String, email: String) {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(email, token)
        editor.commit()
    }

    fun login (email: String, id_token: String) {

        (application as MasterApplication).service.googleLogin(Id_token(id_token)).enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.d("LOGIN fail", "통신 실패")
            }
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful.not()) {
                    Log.d("LOGIN fail", "응답 실패")
                    return
                }
                val userStatus = response.body()?.googleConfirm
                val userJWTtoken = response.body()?.jwtToken
                Log.d("LOGIN info", "userStatus : ${userStatus}")
                Log.d("LOGIN info", "JWT token : ${userJWTtoken}")

                //JWT_token 저장
                if (userJWTtoken != null) {
                    saveUserToken(userJWTtoken, email)
                }
                val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
                val savedToken = sp.getString(email, "null")
                if (savedToken != null) {
                    Token.token = savedToken
                }

                if (userStatus == "CONFIRM_USER"){
                    startActivity(Intent(this@LoginActivity, CalenderActivity::class.java))
                    finish()
                }
                else { //userStatus == "NEW_USER" or "EXIST_USER"
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                    finish()
                }
            }
        })
    }
}


