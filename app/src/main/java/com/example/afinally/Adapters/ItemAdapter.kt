package com.example.afinally.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinally.DataClasses.ItemData
import com.example.afinally.R

class ItemAdapter (var mList: List<ItemData>) :
    RecyclerView.Adapter<ItemAdapter.viewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class viewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val qty: TextView = itemView.findViewById(R.id.tvQty)
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_card_view, parent, false)



        return viewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.name.text = mList[position].name
        holder.qty.text = mList[position].qty
        holder.price.text = mList[position].price
    }
}