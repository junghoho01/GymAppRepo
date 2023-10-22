package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityRecommendationAndReportBinding

class RecommendationAndReportActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecommendationAndReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationAndReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWorkoutRecommendation.setOnClickListener {
            toWorkoutRecommendation()
        }

        binding.btnMealRecommendation.setOnClickListener {
            toMealRecommendation()
        }

        binding.btnHealthRecommendation.setOnClickListener {
            toMonthlyHealthReports()
        }

        binding.btnRiskPrediction.setOnClickListener {
            toHealthRiskPrediction()
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

    private fun toHealthRiskPrediction() {
        var intent = Intent(this, HealthRiskPredictionActivity::class.java)
        startActivity(intent)
    }

    private fun toMonthlyHealthReports() {
        var intent = Intent(this, MonthlyHealthReportActivity::class.java)
        startActivity(intent)
    }

    private fun toMealRecommendation() {
        var intent = Intent(this, MealRecommendationActivity::class.java)
        startActivity(intent)
    }

    private fun toWorkoutRecommendation() {
        var intent = Intent(this, WorkoutRecommendationActivity::class.java)
        startActivity(intent)
    }
}