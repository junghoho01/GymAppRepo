package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityMealsAndNutritionTwoBinding

class MealsAndNutritionTwoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealsAndNutritionTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsAndNutritionTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearchRecipe.setOnClickListener{
            toSearchRecipe()
        }
    }

    private fun toSearchRecipe() {
        intent = Intent(this, SearchRecipeActivity::class.java)
        startActivity(intent)

    }
}