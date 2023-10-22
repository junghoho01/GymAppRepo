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

        // btmNavigation
        binding.navSection1.setOnClickListener {
            var intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection2.setOnClickListener {
            var intent = Intent(this, MealsAndNutritionTwoActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection3.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection4.setOnClickListener {
            var intent = Intent(this, RecommendationAndReportActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection5.setOnClickListener {
            var intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
            finish()
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