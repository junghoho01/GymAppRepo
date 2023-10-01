package com.example.gymbetaapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymbetaapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val email = intent.getStringExtra("EMAIL_KEY").toString() // For string data
        var flag = intent.getStringExtra("FLAG_KEY").toString() // For string data

        if (flag == "1"){
            DialogUtils.showCustomDialog(this, "Register Successfully, Welcome !")
            flag = "0"
        }

        binding.btnWorkout.setOnClickListener {
            toWorkout()
        }

        binding.btnMealsAndNutrition.setOnClickListener {
            toMealsAndNutrition()
        }
    }

    private fun toMealsAndNutrition() {
        var intent = Intent(this, MealsAndNutritionTwoActivity::class.java)
        startActivity(intent)
    }

    private fun toWorkout() {
        var intent = Intent(this, WorkoutActivity::class.java)
        startActivity(intent)
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish() // Exit the app
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000) // Reset the flag after 2 seconds
        }
    }
}