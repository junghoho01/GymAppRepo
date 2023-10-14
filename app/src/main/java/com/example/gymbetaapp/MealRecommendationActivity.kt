package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityMealRecommendationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MealRecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealRecommendationBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            toRecommendationAndReport()
        }

        // Get email
        // Retrieve data from shared preferences
        val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null).toString()

        val ref = db.collection("user").document(userEmail)
        ref.get().addOnSuccessListener {
            if (it != null){
                // Get data
                val height = it.data?.get("height").toString()
                val weight = it.data?.get("weight").toString()
                var bmi = checkBMI(height, weight).toInt()
                //Toast.makeText(this, bmi.toString(), Toast.LENGTH_SHORT).show()

                if(bmi < 18){
                    binding.ivBreakfast.setImageResource(R.drawable.multigrainsandwhich)
                    binding.ivLunch.setImageResource(R.drawable.bakedsalmonwithvegetables)
                    binding.ivDinner.setImageResource(R.drawable.beeffry)
                    binding.tvBreakfast.text = "Multigrain Sandwhich"
                    binding.tvLunch.text = "Baked Salmon Vege"
                    binding.tvDinner.text = "Beef Fry"
                } else if (bmi < 25 ){
                    binding.ivBreakfast.setImageResource(R.drawable.pancake)
                    binding.ivLunch.setImageResource(R.drawable.salmonrice)
                    binding.ivDinner.setImageResource(R.drawable.chickenfruit)
                    binding.tvBreakfast.text = "Banana Pancake"
                    binding.tvLunch.text = "Salmon Rice"
                    binding.tvDinner.text = "Chicken Fruit"
                } else {
                    binding.ivBreakfast.setImageResource(R.drawable.poppyseedsalad)
                    binding.ivLunch.setImageResource(R.drawable.romainesalad)
                    binding.ivDinner.setImageResource(R.drawable.kengsom)
                    binding.tvBreakfast.text = "Poppy Seed Salad"
                    binding.tvLunch.text = "Romaine Salad"
                    binding.tvDinner.text = "Keng Som Shrimp"
                }

            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun toRecommendationAndReport() {
        var intent = Intent(this, RecommendationAndReportActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkBMI(height: String, weight: String): Double {
        var getHeight = height.toInt()
        var getWeight = weight.toInt()

        // Convert height from centimeters to meters
        val heightM = getHeight / 100.0

        // Calculate BMI using the formula: weight (kg) / (height (m) * height (m))
        return getWeight / (heightM * heightM)
    }
}