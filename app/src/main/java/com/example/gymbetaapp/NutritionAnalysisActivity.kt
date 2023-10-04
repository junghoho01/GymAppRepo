package com.example.gymbetaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.gymbetaapp.databinding.ActivityNutritionAnalysisBinding
import org.json.JSONArray
import org.json.JSONObject

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
//        // Split the ingredients by ", " and trim whitespace
//        val ingredientList = ingredients.split(",\n").map { it.trim() }
//
//        // Create a JSON array of ingredients
//        val jsonArray = JSONArray()
//        for (ingredient in ingredientList) {
//            jsonArray.put(ingredient)
//        }
//
//        // Log the JSON data before making the API request
//        Log.d("API Request", jsonArray.toString())

        val edamamApiClient = EdamamApiClient()
        edamamApiClient.fetchNutritionData(ingredients, object : NutritionCallback {
            override fun onSuccess(response: NutritionResponse?) {
                // Handle the successful response here
                if (response != null) {
                    println("Calories: ${response.calories}")
                    println("Total Nutrients: ${response.totalNutrients}")
                    println("Total Daily: ${response.totalDaily}")
//                    binding.tvCalories.text = response.calories.toString()

                    // Retrieve total FAT
                    val totalFat = response.totalNutrients["FAT"]
                    if (totalFat != null) {
                        val fatLabel = totalFat.label
                        val fatQuantity = totalFat.quantity
                        val fatUnit = totalFat.unit
                        println("Total Fat: $fatQuantity $fatUnit ($fatLabel)")

                        // Now, you can set the total fat to your TextView
                        binding.tvCalories.text = "$fatQuantity $fatUnit"
                    }
                }
            }

            override fun onError(error: String) {
                // Handle the error here
                println("Error: $error")
            }
        })
    }
}