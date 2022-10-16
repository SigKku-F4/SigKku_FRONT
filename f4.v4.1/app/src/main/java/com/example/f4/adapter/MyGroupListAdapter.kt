package com.example.f4.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Mygroup
import com.example.f4.data.Userinfo
import com.example.f4.view.MyGroupActivity
import com.example.f4.view.MyGroupCalendarActivity

class MyGroupListAdapter(
    val context: Context?, private val data: Mygroup?
): RecyclerView.Adapter<MyGroupListAdapter.ItemViewHolder>() {

    var mPosition = 0

    private fun setPosition(position:Int){
        mPosition = position
    }

    class ItemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val profile = itemView.findViewById<ImageView>(R.id.grpMemIc1)
        val nickname = itemView.findViewById<TextView>(R.id.userNickname)
        val green = itemView.findViewById<TextView>(R.id.greenStamp)
        val yellow = itemView.findViewById<TextView>(R.id.yellowStamp)
        val red = itemView.findViewById<TextView>(R.id.redStamp)
    }

    // 아이템 하나가 들어갈 뷰를 만들고 뷰 홀더에 넣어줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.my_group_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    // 뷰를 그리는 부분
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Userinfo = data?.userinfo!!.get(position)

        if (data.userinfo.get(position).profile == "SO") {
            holder.profile.setImageResource(R.drawable.profile4)
        }
        else if (data.userinfo.get(position).profile == "DONG") {
            holder.profile.setImageResource(R.drawable.profile2)
        }
        else if (data.userinfo.get(position).profile == "BEE") {
            holder.profile.setImageResource(R.drawable.profile1)
        }
        else if (data.userinfo.get(position).profile == "YOU") {
            holder.profile.setImageResource(R.drawable.profile3)
        }

        holder.nickname.setText(data.userinfo.get(position).userNickName)
        holder.green.setText(data.userinfo.get(position).greenStamp.toString())
        holder.yellow.setText(data.userinfo.get(position).yellowStamp.toString())
        holder.red.setText(data.userinfo.get(position).redStamp.toString())

        // 그룹원 클릭 시 해당 그룹원의 캘린더로 이동
        holder.itemView.setOnClickListener { view ->
            setPosition(position)

            val intent = Intent(context, MyGroupCalendarActivity::class.java)
            val activity: MyGroupActivity = context as MyGroupActivity

            intent.putExtra("groupId", data.groupId) // groupId
            intent.putExtra("userId", data.userinfo.get(position).userId) // userId
            intent.putExtra("userNickName", data.userinfo.get(position).userNickName) // userNickName
            intent.putExtra("groupCur", itemCount) // groupCurrent
            context?.startActivity(intent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (data?.userinfo != null) {
            size = data?.userinfo.size
        }
        return size
    }
}