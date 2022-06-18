package com.example.mentalarithmetic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalarithmetic.data.UsersList

class SearchRecyclerAdapter(private val users: UsersList) : RecyclerView
.Adapter<SearchRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var GroupName: TextView = itemView.findViewById(R.id.GroupNameRV)
        var mail: TextView = itemView.findViewById(R.id.mailRV)
        var raiting: TextView = itemView.findViewById(R.id.raitingRV)
        var status: TextView = itemView.findViewById(R.id.statusRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.GroupName.text ="Группа: "+ users.GroupName
        holder.mail.text ="email: "+ users.mail
        if (users.raiting==-1)
            holder.raiting.text ="Рейтинг отсутствует"
        else
            holder.raiting.text ="Рейтинг: "+ users.raiting.toString()
        holder.status.text ="Статус: "+ users.status
    }

    override fun getItemCount() = 1
}