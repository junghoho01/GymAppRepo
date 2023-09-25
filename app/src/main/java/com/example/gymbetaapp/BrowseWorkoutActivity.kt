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

    }

    private fun toBinding() {
        var intent = Intent(this, SubBrowseWorkoutsActivity::class.java)
        startActivity(intent)
    }
}