package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWorkoutRecommendationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WorkoutRecommendationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWorkoutRecommendationBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutRecommendationBinding.inflate(layoutInflater)
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
                var bmi = checkBMI(height, weight).toInt()
                //Toast.makeText(this, bmi.toString(), Toast.LENGTH_SHORT).show()

                if(bmi < 18){
                    binding.tvJogsReps.text = "15 reps x 5 sets"
                    binding.tvSquatReps.text = "15 reps x 5 sets"
                    binding.tvPushUpReps.text = "15 reps x 5 sets"
                } else if (bmi < 25 ){
                    binding.tvJogsReps.text = "9 reps x 3 sets"
                    binding.tvSquatReps.text = "9 reps x 3 sets"
                    binding.tvPushUpReps.text = "9 reps x 3 sets"
                } else {
                    binding.tvJogsReps.text = "12 reps x 5 sets"
                    binding.tvSquatReps.text = "12 reps x 5 sets"
                    binding.tvPushUpReps.text = "12 reps x 5 sets"
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