package com.example.gymbetaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter (private val subscribeuser_list: ArrayList<User>) : RecyclerView.Adapter<MemberAdapter.MyViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(position: Int)

        // Delete button
        fun onDeleteClick(position: Int)
    }

    private lateinit var mListener : MemberAdapter.onItemClickListener


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.subscribeuser_list,parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MemberAdapter.MyViewHolder, position: Int) {
        val user: User = subscribeuser_list[position]
        holder.name.text = user.username

        // Handle "Delete" button click
        holder.deleteButton.setOnClickListener {
            mListener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return subscribeuser_list.size
    }

    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tv_username)
        val deleteButton : Button = itemView.findViewById(R.id.btn_delete)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}