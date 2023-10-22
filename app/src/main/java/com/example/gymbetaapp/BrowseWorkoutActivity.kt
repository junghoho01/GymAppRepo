package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityBrowseWorkoutBinding

class BrowseWorkoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBrowseWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAbs.setOnClickListener {
            toBinding()
        }

        binding.btnBack.setOnClickListener {
            toBack()
        }

        binding.btnBiceps.setOnClickListener {
            toBiceps()
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

    private fun toBiceps() {
        var intent = Intent(this, SubBicepsActivity::class.java)
        startActivity(intent)
    }

    private fun toBack() {
        var intent = Intent(this, SubBackActivity::class.java)
        startActivity(intent)
    }

    private fun toBinding() {
        var intent = Intent(this, SubBrowseWorkoutsActivity::class.java)
        startActivity(intent)
    }
}