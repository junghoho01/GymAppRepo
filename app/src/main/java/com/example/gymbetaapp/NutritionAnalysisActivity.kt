package com.example.gymbetaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityNutritionAnalysisBinding

class NutritionAnalysisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNutritionAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnalyse.setOnClickListener {
            var ingredients = binding.etAnalyseContent.text.toString()
            analyseFood(ingredients)
        }
    }

    private fun analyseFood(ingredients: String) {
        val ingredientsFood = ingredients.split(", ")

        // Add a newline character after each ingredient
        val formattedIngredients = ingredientsFood.joinToString(",\n")

        val edamamApiClient = EdamamApiClient()
        edamamApiClient.fetchNutritionData(formattedIngredients, object : NutritionCallback {
            override fun onSuccess(response: NutritionResponse?) {
                // Handle the successful response here
                if (response != null) {
                    println("Calories: ${response.calories}")
                    println("Total Nutrients: ${response.totalNutrients}")
                    println("Total Daily: ${response.totalDaily}")
                }
            }

            override fun onError(error: String) {
                // Handle the error here
                println("Error: $error")
            }
        })
    }
}