package com.example.gymbetaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TestAPI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)

        val edamamApiClient = EdamamApiClient()

        edamamApiClient.fetchNutritionData("1 cup rice, 10 oz chickpeas", object : NutritionCallback {
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