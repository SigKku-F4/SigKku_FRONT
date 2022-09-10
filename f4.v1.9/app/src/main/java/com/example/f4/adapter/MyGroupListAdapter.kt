package com.example.f4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.view.MyGroupData

class MyGroupListAdapter(
    private val context: Context, private val groupDataList: ArrayList<MyGroupData>
): RecyclerView.Adapter<MyGroupListAdapter.ItemViewHolder>() {
    var mPosition = 0

//    fun GetPosition():Int {
//        return mPosition
//    }

    private fun setPosition(position:Int){
        mPosition = position
    }

//    fun addItem(pleeList: MyGroupData){
//        groupDataList.add(pleeList)
//        notifyDataSetChanged()
//    }
//
//    fun removeItem(position: Int){
//        if (position > 0) {
//            groupDataList.removeAt(position)
//            //notifyItemRemoved(position)
//            notifyDataSetChanged()
//        }
//    }

    inner class ItemViewHolder(itemView:View):
            RecyclerView.ViewHolder(itemView) {
        private val grMemIc = itemView.findViewById<ImageView>(R.id.grpMemIc1) // pleePhoto
        private val nickname = itemView.findViewById<TextView>(R.id.userNickname) // pleeNum
        private val green = itemView.findViewById<TextView>(R.id.greenStamp) // pleeName
        private val yellow = itemView.findViewById<TextView>(R.id.yellowStamp) // pleeName
        private val red = itemView.findViewById<TextView>(R.id.redStamp) // pleeName


        fun bind(pleeList: MyGroupData, context: Context) {
            if (pleeList.myIcon != "") {
                val resourceId = context.resources.getIdentifier(pleeList.myIcon, "drawable", context.packageName)

                if (resourceId > 0) {
                    grMemIc.setImageResource(resourceId)
                    //tv에 데이터 세팅
                    green.text = pleeList.green.toString()
                    yellow.text = pleeList.yellow.toString()
                    red.text = pleeList.red.toString()
                } else {
                    grMemIc.setImageResource(R.mipmap.ic_launcher_round)
                }
            } else { // 데이터 없을 때..?
                grMemIc.setImageResource(R.drawable.ic_cancel)
                green.setText("???")
                yellow.setText("???")
                red.setText("???")
            }
            nickname.text = pleeList.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.my_group_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupDataList.size
    }


    // 뷰와 데이터의 결합.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(groupDataList[position], context)
//        val grl = groupDataList[position]
        holder.itemView.setOnClickListener { view ->
            setPosition(position)
            Toast.makeText(view.context, "$position 클릭!!!", Toast.LENGTH_SHORT).show()

            //플리 클릭하면 확대 페이지 보여주기(다른 activity로 넘어가기)
//            val intent = Intent(context, GroupActivity::class.java)
//            intent.putExtra("image_name", dataList[position].photo) //이미지 data intent에 넣기
//            intent.putExtra("plee_name", dataList[position].name)
//            intent.putExtra("plee_info", dataList[position].info)
//            context.startActivity(intent) //intent 시작
        }
   }
}