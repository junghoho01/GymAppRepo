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

        binding.btnMealsNutritionAnalysis.setOnClickListener {
            toTestAPI()
        }

        binding.btnTrackNutritionGain.setOnClickListener {
            toTrackNutritionGain()
        }
    }

    private fun toTrackNutritionGain() {
        intent = Intent(this, TrackNutritionGain::class.java)
        startActivity(intent)
    }

    private fun toTestAPI() {
        intent = Intent(this, NutritionAnalysisActivity::class.java)
        startActivity(intent)
    }

    private fun toSearchRecipe() {
        intent = Intent(this, SearchRecipeActivity::class.java)
        startActivity(intent)

    }
}