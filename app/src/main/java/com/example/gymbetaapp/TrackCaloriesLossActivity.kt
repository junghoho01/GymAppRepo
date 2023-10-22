package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.gymbetaapp.databinding.ActivityTrackCaloriesLossBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TrackCaloriesLossActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTrackCaloriesLossBinding
    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackCaloriesLossBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from shared preferences
        val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null)

        binding.btnEditCalories.setOnClickListener {
            DialogUtils.editCaloriesDialog(this, userEmail.toString()) {
                // Refresh the activity
                val intent = Intent(this, TrackCaloriesLossActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        var db = Firebase.firestore

        val userId = userEmail.toString()
        val ref = db.collection("user").document(userId)
        var BMR = 0
        val currentDate = Date()
        val formattedDate = formatDate(currentDate, "yyyy-MM-dd")

        ref.get().addOnSuccessListener {
            if (it != null){
                // Get data
                val weight = it.data?.get("weight")?.toString().toString()
                val age = it.data?.get("age")?.toString().toString()
                val height = it.data?.get("height")?.toString().toString()
                val calorieBurnt = it.data?.get("caloriesBurnt")?.toString().toString()
                val calorieDate = it.data?.get("caloriesDate")?.toString().toString()
                val gender = it.data?.get("gender")?.toString().toString()

                if(calorieDate != formattedDate){
                    val userMap = mapOf(
                        "caloriesBurnt" to "0",
                        "caloriesDate" to formattedDate
                    )

                    db.collection("user").document(userId).update(userMap)
                }

                if(gender == "Male"){
                    BMR = menBMR(weight, height, age).toInt()
                } else {
                    BMR = womenBMR(weight, height, age).toInt()
                }

                binding.tvCompare.text = "$calorieBurnt kcal / $BMR kcal"
                binding.tvToLoss.text = "Calories Loss: $BMR kcal"

                var progressLimit = 100.0 / BMR.toDouble()
                progressLimit *= calorieBurnt.toInt()

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
            }
        }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
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

    private fun menBMR(weight: String, height: String, age: String): Number {
        var weight = weight.toInt()
        var height = height.toInt()
        var age = age.toInt()

        var BMR = 66 + (6.2 * weight) + (12.7 * height) - (6.76 * age)
        return BMR.toInt()
    }

    private fun womenBMR(weight: String, height: String, age: String): Number {
        var weight = weight.toInt()
        var height = height.toInt()
        var age = age.toInt()

        var BMR = 655.1 + (4.35 * weight) + (4.7 * height) - (4.7 * age)
        return BMR.toInt()
    }

    fun formatDate(date: Date, format: String): String {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }
}