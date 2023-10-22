package com.example.gymbetaapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityDetailWorkoutBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class DetailWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the intent that started this activity
        val intent = intent
        val info = intent.getStringExtra("info").toString()
        val title = intent.getStringExtra("title").toString()
        val pic = intent.getStringExtra("pic").toString()
        val calories = intent.getStringExtra("CALORIES_KEY").toString()
        val videoName = intent.getStringExtra("VIDEO_KEY").toString()

//       Toast.makeText(this, calories, Toast.LENGTH_SHORT).show()

        // Retrieve data from shared preferences
        val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null).toString()

//        Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show()

        binding.tvTitle.text = title

        binding.btnInfo.setOnClickListener {
            DialogUtils.infoDialog(this, info)
        }

        binding.btnVideo.setOnClickListener {
            DialogUtils.videoDialog(this, videoName)
        }

        binding.btnAddCalories.setOnClickListener {
            DialogUtils.caloriesDialog(this, userEmail, calories)
        }

        showImage(pic)

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

    private fun showImage(pic: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageRef = FirebaseStorage.getInstance().reference.child("images/$pic.jpg")
        val localfile = File.createTempFile("tempFile", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {

            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.ivWorkoutImage.setImageBitmap(bitmap)
        }.addOnFailureListener {

            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }

            Toast.makeText(this, "Error Retrieve Image", Toast.LENGTH_SHORT).show()
        }
    }
}