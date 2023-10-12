package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityWorkoutRecommendationBinding

class WorkoutRecommendationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWorkoutRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            toRecommendationAndReport()
        }
    }

    private fun toRecommendationAndReport() {
        var intent = Intent(this, RecommendationAndReportActivity::class.java)
        startActivity(intent)
        finish()
    }
}