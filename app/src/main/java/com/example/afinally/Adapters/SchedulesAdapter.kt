package com.example.afinally.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinally.DataClasses.Schedule
import com.example.afinally.R

class SchedulesAdapter (var mList: List<Schedule>) :
    RecyclerView.Adapter<SchedulesAdapter.viewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class viewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_schedules, parent, false)



        return viewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

    }
}