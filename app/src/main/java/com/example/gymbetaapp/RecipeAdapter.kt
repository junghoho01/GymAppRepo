package com.example.gymbetaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val recipeList: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list,parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: RecipeAdapter.MyViewHolder, position: Int) {
        val recipe : Recipe = recipeList[position]
        holder.name.text = recipe.name
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tv_foodName)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}