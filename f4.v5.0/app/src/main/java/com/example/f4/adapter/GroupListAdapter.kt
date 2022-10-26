package com.example.f4.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Maingroup


class GroupListAdapter(
    val context: Context?, private val groupList: List<Maingroup>?
): RecyclerView.Adapter<GroupListAdapter.ItemViewHolder>() {

    var mPosition = 0

    private fun setPosition(position:Int){
        mPosition = position
    }

    class ItemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val grname = itemView.findViewById<TextView>(R.id.groupName)
        val cursize = itemView.findViewById<TextView>(R.id.groupCurrentSize)
        val name1 = itemView.findViewById<TextView>(R.id.name1)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.group_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Maingroup = groupList!!.get(position)
        var totalName: String? = ""

        holder.grname.setText(groupList?.get(position)?.groupName)
        holder.cursize.setText(groupList?.get(position)?.groupCurrentSize.toString())
        for (i in 0..groupList?.get(position)?.userNickname.size - 1) {
            totalName += groupList?.get(position)?.userNickname[i]
            if (i < groupList?.get(position)?.userNickname.size - 1) {
                totalName += ", "
            }
        }
        holder.name1.setText(totalName)

        holder.itemView.setOnClickListener { view ->
            setPosition(position)

            val intent = Intent(context, MyGroupActivity::class.java)
            intent.putExtra("groupCur", groupList?.get(position)?.groupCurrentSize) // putExtra - (저장될 값의 키, 저장될 값)
            intent.putExtra("groupId", groupList?.get(position)?.groupId)
            context?.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (groupList != null) {
            size = groupList.size
        }
        return size
    }


}