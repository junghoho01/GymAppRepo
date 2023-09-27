package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWorkout.setOnClickListener {
            toBrowseWorkoutList()
        }

        binding.btnTrackCalories.setOnClickListener {
            toTrackCalories()
        }
    }

    private fun toTrackCalories() {
        var intent = Intent(this, TrackCaloriesLossActivity::class.java)
        startActivity(intent)
    }

    private fun toBrowseWorkoutList() {
        var intent = Intent(this, BrowseWorkoutActivity::class.java)
        startActivity(intent)
    }


}