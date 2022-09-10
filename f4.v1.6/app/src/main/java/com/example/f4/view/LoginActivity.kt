package com.example.f4.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.f4.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity() {

    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //구글 로그인 시 사용자의 데이터 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //이미 로그인이 되어 있는 지 확인
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            Log.e("Google account", "로그인 되지 않은 상태")
        } else {
            Log.e("Google account", "로그인 완료된 상태")
        }

        //로그인 버튼 사이즈 (길게)
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
            val name = account?.displayName.toString()
            //var googletoken = account?.idToken.toString()
            //var googletokenAuth = account?.serverAuthCode.toString()
            Log.e("Google account",email)
            Log.e("Google account",name)
        } catch (e: ApiException){
            Log.e("Google account","signInResult:failed Code = " + e.statusCode)
        }
    }
}
