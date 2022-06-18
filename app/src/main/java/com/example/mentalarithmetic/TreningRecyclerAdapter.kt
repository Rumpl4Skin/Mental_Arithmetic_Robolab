package com.example.mentalarithmetic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TreningRecyclerAdapter(private val names: List<String>, onClickListener : OnStateClickListener) : RecyclerView
.Adapter<TreningRecyclerAdapter.MyViewHolder>() {

     var onClickListener : OnStateClickListener=onClickListener

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var treningTxt: TextView = itemView.findViewById(R.id.trening_txt)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.trening_item, parent, false)
            return MyViewHolder(itemView)
        }

    interface OnStateClickListener {
        fun onStateClick(game: String?, position: Int)
    }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.treningTxt.text = names[position]
            holder.itemView.setOnClickListener {
                onClickListener.onStateClick(names[position], position)
            }
        }

        override fun getItemCount() = names.size

}