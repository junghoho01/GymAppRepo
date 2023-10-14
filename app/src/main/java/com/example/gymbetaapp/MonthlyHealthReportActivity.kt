package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityMonthlyHealthReportBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MonthlyHealthReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonthlyHealthReportBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyHealthReportBinding.inflate(layoutInflater)
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
                    binding.imgLogo.setImageResource(R.drawable.baseline_warning_24)
                    binding.tvStatus.text = "Your health is in below expected state!"
                    binding.tvHealthReport.text = "You did not kept up with the recommendations and did not maintained good health for this month."
                    "Keep up the good work and strive to improve your health even further"
                } else if (bmi < 25 ){
                    binding.imgLogo.setImageResource(R.drawable.baseline_check_24)
                    binding.tvStatus.text = "You are in good health!"
                    binding.tvHealthReport.text = "You have kept up with the recommendations and have maintained good health for this month."
                            "Keep up the good work and strive to improve your health even further"
                } else {
                    binding.imgLogo.setImageResource(R.drawable.baseline_warning_24)
                    binding.tvStatus.text = "Your health exceed the expected state"
                    binding.tvHealthReport.text = "You did not kept up with the recommendations and did not maintained good health for this month."
                    "Keep up the good work and strive to improve your health even further"
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