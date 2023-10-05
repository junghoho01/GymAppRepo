package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityTrackNutritionGainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TrackNutritionGain : AppCompatActivity() {

    private lateinit var binding: ActivityTrackNutritionGainBinding
    private var progressStatus = 0
    private var progressStatus2 = 0
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackNutritionGainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from shared preferences
        val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null)

        binding.btnEditNutrition.setOnClickListener {
            DialogUtils.editGainsDialog(this, userEmail.toString()){
                // Refresh the activity
                val intent = Intent(this, TrackNutritionGain::class.java)
                startActivity(intent)
                finish()
            }
        }

        var db = Firebase.firestore

        val userId = userEmail.toString()
        val ref = db.collection("user").document(userId)
        var BMR = 0
        ref.get().addOnSuccessListener {
            if (it != null){
                // Get data
                val weight = it.data?.get("weight")?.toString().toString()
                val age = it.data?.get("age")?.toString().toString()
                val height = it.data?.get("height")?.toString().toString()
                val calorieGained = it.data?.get("caloriesGained")?.toString().toString()
                val proteinGained = it.data?.get("proteinGained")?.toString().toString()
                val gender = it.data?.get("gender")?.toString().toString()

                if(gender == "Male"){
                    BMR = menBMR(weight, height, age).toInt()
                } else {
                    BMR = womenBMR(weight, height, age).toInt()
                }

                binding.tvCompare.text = "$calorieGained kcal"
                binding.tvCompareProtein.text = "$proteinGained gram"


                var neededProtein = weight.toInt() * (0.8)
                var numberProtein = neededProtein.toString()
                binding.tvCaloriesToGain.text = "Calories: $BMR kcal"
                binding.tvProteinToGain.text = "Protein: $numberProtein gram"

//                var progressLimit = 100.0 / BMR.toDouble()
                var progressLimit = 100.0 / BMR.toDouble()
                progressLimit *= calorieGained.toInt()

                var progressLimit2 = 100.0 / neededProtein
                progressLimit2 *= proteinGained.toInt()

                // Start a thread to update the progress
                Thread {
                    while (progressStatus < progressLimit) {
                        progressStatus++
                        handler.post {
                            // Update the progress bar
                            binding.progressBar.progress = progressStatus
                        }
                        try {
                            // Sleep for a short duration to control the progress update speed
                            Thread.sleep(50)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }.start()

                // Start a thread to update the progress
                Thread {
                    while (progressStatus2 < progressLimit2) {
                        progressStatus2++
                        handler.post {
                            // Update the progress bar
                            binding.progressBar2.progress = progressStatus2
                        }
                        try {
                            // Sleep for a short duration to control the progress update speed
                            Thread.sleep(50)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }.start()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun menBMR(weight: String, height: String, age: String): Number {
        var weight = weight.toInt()
        var height = height.toInt()
        var age = age.toInt()

        var BMR = 66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age)
        return BMR.toInt()
    }

    private fun womenBMR(weight: String, height: String, age: String): Number {
        var weight = weight.toInt()
        var height = height.toInt()
        var age = age.toInt()

        var BMR = 665.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age)
        return BMR.toInt()
    }
}