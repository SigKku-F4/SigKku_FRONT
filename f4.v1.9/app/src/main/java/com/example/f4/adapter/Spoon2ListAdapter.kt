package com.example.f4.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.view.Spoon2Data

class Spoon2ListAdapter(val context: Context, val recFoodList: ArrayList<Spoon2Data>) :
RecyclerView.Adapter<Spoon2ListAdapter.ViewHolder>(), Filterable {
    var TAG = "RecommendFoodListAdapter"

    var filteredFood = ArrayList<Spoon2Data>()
    var itemFilter = ItemFilter()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recFoodName: TextView
        var recFoodKcal: TextView
        var recCar: TextView
        var recPro: TextView
        var recFat: TextView

        init {
            recFoodName = itemView.findViewById(R.id.recFoodName)
            recFoodKcal = itemView.findViewById(R.id.recFoodKcal)
            recCar = itemView.findViewById(R.id.recCar)
            recPro = itemView.findViewById(R.id.recPro)
            recFat = itemView.findViewById(R.id.recFat)

            itemView.setOnClickListener {
                Toast.makeText(context, "상세화면이 뜰거에요", Toast.LENGTH_SHORT).show()

            }
        }
    }

    init {
        filteredFood.addAll(recFoodList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.spoon2_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = filteredFood[position]
        //[수정요함] 이미지 작업의 경우 glide를 사용해 server의 image를 불러올 것
        //holder.iv_person_phone_book_list_item
        holder.recFoodName.text = food.foodName
        holder.recFoodKcal.text = food.kcal.toString()
        holder.recCar.text = food.car.toString()
        holder.recPro.text = food.pro.toString()
        holder.recFat.text = food.fat.toString()
    }

    override fun getItemCount(): Int {
        return filteredFood.size
    }
// -- filter
    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<Spoon2Data> = ArrayList<Spoon2Data>()
            //공백제외 아무런 값이 없을 경우 -> 밑의 주석 지우면 원본 배열이 나옴.
            if (filterString.trim { it <= ' ' }.isEmpty()) {
//                results.values = recFoodList
//                results.count = recFoodList.size
//
//                return results
            }
            //공백제외 7글자 이하인 경우 -> 이름으로만 검색
            else if (filterString.trim { it <= ' ' }.length <= 7) {
                for (foodname in recFoodList) {
                    if (foodname.foodName.contains(filterString)) {
                        filteredList.add(foodname)
                    }
                }
            }
//            //공백제외 2글자 이하인 경우 -> 이름으로만 검색
//            else if (filterString.trim { it <= ' ' }.length <= 2) {
//                for (foodname in recFoodList) {
//                    if (foodname.foodName.contains(filterString)) {
//                        filteredList.add(foodname)
//                    }
//                }
//            }
            //그 외의 경우(공백제외 2글자 초과) -> 이름/전화번호로 검색
//            else {
//                for (foodname in recFoodList) {
//                    if (foodname.name.contains(filterString) || foodname.phoneNumber.contains(filterString)) {
//                        filteredList.add(foodname)
//                    }
//                }
//            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredFood.clear()
            filteredFood.addAll(filterResults.values as ArrayList<Spoon2Data>)
            notifyDataSetChanged()
        }
    }

}