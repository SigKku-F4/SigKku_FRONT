package com.example.f4.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.f4.R
import com.example.f4.SignUpActivity
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.data.Id_token
import com.example.f4.data.Login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        val email: String? = account?.email
        val id_token: String? = account?.idToken

        if (account == null) {
        } else {
            login(email.toString(), id_token.toString())
        }

        val google_login_button = findViewById<SignInButton>(R.id.googleLoginBtn)
        google_login_button.setSize(SignInButton.SIZE_WIDE)

        GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        google_login_button.setOnClickListener {
            var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
            GoogleSignResultLauncher.launch(signIntent)
        }

    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var idToken = account?.idToken.toString()

            login(email, idToken)

        } catch (e: ApiException){
        }
    }

    fun saveUserToken(token: String, email: String) {
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(email, token)
        editor.commit()
    }

    fun login (email: String, id_token: String) {

        (application as MasterApplication).service.googleLogin(Id_token(id_token)).enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
            }
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful.not()) {
                    return
                }
                val userStatus = response.body()?.googleConfirm
                val userJWTtoken = response.body()?.jwtToken
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


