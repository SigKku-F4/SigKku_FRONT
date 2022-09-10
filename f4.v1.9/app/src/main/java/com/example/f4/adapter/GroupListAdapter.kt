package com.example.f4.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.view.GroupData
import com.example.f4.view.MyGroupActivity

class GroupListAdapter(
    private val context: Context, private val groupDataList: ArrayList<GroupData>
): RecyclerView.Adapter<GroupListAdapter.ItemViewHolder>() {
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

    inner class ItemViewHolder(itemView: View?):
        RecyclerView.ViewHolder(itemView!!) {
        private val grname = itemView?.findViewById<TextView>(R.id.groupName)
        private val cursize = itemView?.findViewById<TextView>(R.id.groupCurrentSize)
        private val name1 = itemView?.findViewById<TextView>(R.id.name1)

        fun bind(pleeList: GroupData) { //, context: Context) {
//            if (pleeList.groupName != "") {
//                val resourceId = context.resources.getIdentifier(pleeList.groupName, "drawable", context.packageName)
//
//                if (resourceId > 0) {
//                    grname.setText(resourceId)
//                    //tv에 데이터 세팅
//                    name.text = pleeList.name.toString()
//                }
//            } else { // 데이터 없을 때..?
//                name.setText("???")
//            }
            // TextView와 String 데이터를 연결한다.
            grname?.text = pleeList.groupName
            cursize?.text = pleeList.curSize.toString()
            name1?.text = pleeList.name1
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.group_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupDataList.size
    }


    // 뷰와 데이터의 결합.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(groupDataList[position]) //, context)
//        val grl = groupDataList[position]
        holder.itemView.setOnClickListener { view ->
            setPosition(position)
            //Toast.makeText(view.context, "$position 클릭!!!", Toast.LENGTH_SHORT).show()

            // 내 그룹 액티비티로 이동
            val intent = Intent(context, MyGroupActivity::class.java)
            context.startActivity(intent)


            //플리 클릭하면 확대 페이지 보여주기(다른 activity로 넘어가기)
//            val intent = Intent(context, GroupActivity::class.java)
//            intent.putExtra("image_name", dataList[position].photo) //이미지 data intent에 넣기
//            intent.putExtra("plee_name", dataList[position].name)
//            intent.putExtra("plee_info", dataList[position].info)
//            context.startActivity(intent) //intent 시작
        }
    }

}