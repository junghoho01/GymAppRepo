package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityHealthRiskPredictionBinding
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HealthRiskPredictionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHealthRiskPredictionBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthRiskPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            toRecommendationAndReport()
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
                val age = it.data?.get("age").toString()
                val gender = it.data?.get("gender").toString()

                readPatientData(this, height, weight, age, gender )

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

    private fun readPatientData(
        context: Context,
        height: String,
        weight: String,
        age: String,
        gender: String
    ) {
        // Input values
        val inputAge = age.toInt()
        val inputGender = gender
        val inputWeight = weight.toInt()
        val inputHeight = height.toInt()

        // Get an instance of AssetManager
        val assetManager = context.assets

        // Open the file using AssetManager
        val inputStream = assetManager.open("patientdata.txt")
        val reader = inputStream.bufferedReader()

        // Initialize variables for calculating predictions
        var totalPatients = 0
        var totalPatientsWithDisease = 0

        // Iterate through each line in the file
        reader.forEachLine { line ->
            val tokens = line.split(", ")
            val age = tokens[0].split(": ")[1].toInt()
            val gender = tokens[1].split(": ")[1]
            val weight = tokens[2].split(": ")[1].toDouble()
            val height = tokens[3].split(": ")[1].toDouble()
            val hasChronicDisease = tokens[4].split(": ")[1].toBoolean()

            // Check if the patient matches the input criteria with a tolerance of +-10 units for age, weight, and height
            if (age in (inputAge - 10)..(inputAge + 10) &&
                gender == inputGender &&  // "Any" means any gender is acceptable
                weight >= (inputWeight - 10) && weight <= (inputWeight + 10) &&
                height >= (inputHeight - 10) && height <= (inputHeight + 10)) {
                totalPatients++
                if (hasChronicDisease) {
                    totalPatientsWithDisease++
                }
            }
        }

        // Calculate and print the prediction
        if (totalPatients > 0) {
            val diseaseProbability = totalPatientsWithDisease.toDouble() / totalPatients

            if (diseaseProbability < 0.5){
                binding.imgLogo.setImageResource(R.drawable.baseline_check_24)
                binding.tvStatus.text = "There are no health risk!"
                binding.tvHealthReport.text = "There are no health risks currently because of your good health, keep up the good work and"
                "follow the recommendations to improve your health further"
            } else {
                binding.imgLogo.setImageResource(R.drawable.baseline_warning_24)
                binding.tvStatus.text = "High risk of health risk!"
                binding.tvHealthReport.text = "You did not kept up with the recommendations and did not maintained good health for this month."
                "Keep up the good work and strive to improve your health even further"
            }

            Toast.makeText(this, "The probability of having a chronic disease is: $diseaseProbability", Toast.LENGTH_LONG).show()
        } else {
            binding.imgLogo.setImageResource(R.drawable.baseline_check_24)
            binding.tvStatus.text = "There are no health risk!"
            binding.tvHealthReport.text = "There are no health risks currently because of your good health, keep up the good work and"
            "follow the recommendations to improve your health further"        }
    }
}