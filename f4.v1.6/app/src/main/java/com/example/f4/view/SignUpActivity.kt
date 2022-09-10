package com.example.f4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign.*
import org.w3c.dom.Text


class SignUpActivity : AppCompatActivity() {

    val TAG: String ="Register"
    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        joinBtn.setOnClickListener{

            Log.d(TAG, "회원가입 버튼 클릭")

            val id = joinName.text.toString()
            val age = joinAge.text.toString()
            val height = joinHeight.text.toString()
            val weight = joinWeight.text.toString()
            val goal=joinGoal.text.toString()

            if (id.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || goal.isEmpty()){
                isExistBlank = true
            }


            if(!weight.isEmpty()&&!goal.isEmpty()){
                val weightInt = weight.toInt()
                val goalInt = goal.toInt()

                val result = weightInt - goalInt

                if(weightInt >= goalInt){
                    joinResultText.setText("목표 체중까지 -"+result+"kg")

                }
                else{
                    joinResultText.setText("목표 체중까지 +"+(-result)+"kg")
                }
            }

            if(!isExistBlank){

                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                //db에 유저가 입력한 것들 저장해야되는데 코드 어떻게 짜지..?



                //목표값 - 현재 체중값


                //회원가입 버튼 누르고 화면 넘어가기
//                val intent = Intent(this,MainActivity::class.java)
//                startActivity(intent)
            }
        }

    }
}