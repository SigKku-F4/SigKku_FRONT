package com.example.f4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.view.AddFoodDialData
import kotlinx.android.synthetic.main.add_food_dial_item.view.*

// dataSet의 형식은 서버에서 데이터를 GET해서 오는 것이라면 reponsedata를, 직접 만든 데이터클래스를 사용하고 싶으면 dataclass의 이름을 넣으면 된다.
class AddFoodDialogAdapter(private val nutrientList: ArrayList<AddFoodDialData>) :
        RecyclerView.Adapter<AddFoodDialogAdapter.ViewHolder>() {

    // 뷰홀더를 만들고 뷰를 초기화하는 함수이다. 아직 바인딩이 안되었기 때문에 뷰에 내용이 직접적으로 담기는 과정은 아니다.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AddFoodDialogAdapter.ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.add_food_dial_item, viewGroup, false)

        return AddFoodDialogAdapter.ViewHolder(view)
    }

    // 여기서 뷰와 데이터의 결합이 이루어진다. 만약 이미지 url을 가져오면 이 함수에서 url을 뷰에 넣어서 사진을 출력할 수 있다.(단, 이미지는 Glide와 같은 외부 라이브러리 사용을 추천)
    override fun onBindViewHolder(holder: AddFoodDialogAdapter.ViewHolder, position: Int) {

//        val foodNutrient = view.findViewById<TextView>(R.id.nutrient1)
//        val foodKcalG = view.findViewById<TextView>(R.id.kcalG1)

        val nut = nutrientList[position]
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked:"+nut.nutrient, Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, nut)
            itemView.tag = nut
        }
//        foodNutrient.text = nut.nutrient
//        foodKcalG.text = nut.kcalG
    }

    // 총 몇개의 반복되는 뷰 데이터가 있는지, 즉 친구목록에서 총 친구가 몇 명인지와 같다.
    override fun getItemCount() = nutrientList.size

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, nut: AddFoodDialData) {
            view.nutrient1.text = nut.nutrient
            view.kcalG1.text = nut.kcalG.toString()
            view.setOnClickListener(listener)
        }
    }



}