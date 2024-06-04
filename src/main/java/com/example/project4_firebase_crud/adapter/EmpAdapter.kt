package com.example.project4_firebase_crud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project4_firebase_crud.R
import com.example.project4_firebase_crud.data.EmployeeModel

class EmpAdapter(private val list:ArrayList<EmployeeModel>):RecyclerView.Adapter<EmpAdapter.ViewHolder>() {
    //adapter item listener
    private lateinit var mListenter: onItemClickListenter
    interface onItemClickListenter{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListenter: onItemClickListenter){
        mListenter = clickListenter // de su dung o onCreateViewHolder
    }

    // tao class viewholder
    class ViewHolder(itemview :View, clickListenter: onItemClickListenter):RecyclerView.ViewHolder(itemview){
            init {
                itemview.setOnClickListener{
                    clickListenter.onItemClick(adapterPosition)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView,mListenter)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = list[position]
        holder.itemView.apply {
            val txtEmpName = findViewById<TextView>(R.id.txtNameInList)
            txtEmpName.text = list.empName
        }
    }
}