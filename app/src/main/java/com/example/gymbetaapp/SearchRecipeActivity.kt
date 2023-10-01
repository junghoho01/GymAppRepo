package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymbetaapp.databinding.ActivitySearchRecipeBinding
import com.google.firebase.firestore.*

class SearchRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchRecipeBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipe>
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var db : FirebaseFirestore

    // Search function
    private lateinit var originalRecipeList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recipeArrayList = arrayListOf()
        recipeAdapter = RecipeAdapter(recipeArrayList)
        recyclerView.adapter = recipeAdapter

        EventChangeListener()

        recipeAdapter.setOnItemClickListener(object: RecipeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var name = recipeArrayList[position].name
//                Toast.makeText(this@SearchRecipeActivity, name, Toast.LENGTH_SHORT).show()
                var intent = Intent(this@SearchRecipeActivity, RecipeActivity::class.java)
                intent.putExtra("FOODNAME_KEY", name)
                startActivity(intent)
            }
        })

        binding.btnSearch.setOnClickListener {
            val searchItem = binding.etSearchContent.text.toString().toLowerCase()

            // Filter the original list based on the search query
            val filteredList = originalRecipeList.filter { recipe ->
                recipe.name!!.toLowerCase().contains(searchItem)
            }

            // Update the adapter with the filtered list
            recipeArrayList.clear()
            recipeArrayList.addAll(filteredList)
            recipeAdapter.notifyDataSetChanged()
        }

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("recipe")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }

                    originalRecipeList = ArrayList() // Initialize the original list

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            originalRecipeList.add(dc.document.toObject(Recipe::class.java))
                        }
                    }

                    // Update the adapter with the original list
                    recipeArrayList.clear()
                    recipeArrayList.addAll(originalRecipeList)
                    recipeAdapter.notifyDataSetChanged()
                }
            })
    }

}