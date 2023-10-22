package com.example.gymbetaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val duserList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(position: Int)

        // Delete button
        fun onDeleteClick(position: Int)
    }

    private lateinit var mListener : UserAdapter.onItemClickListener


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.duser_list,parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val user: User = duserList[position]
        holder.name.text = user.username

        // Handle "Delete" button click
        holder.deleteButton.setOnClickListener {
            mListener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return duserList.size
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