package com.example.gymbetaapp

import android.app.ProgressDialog
import android.content.Context
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
            DialogUtils.videoDialog(this, pic)
        }

        binding.btnAddCalories.setOnClickListener {
            DialogUtils.caloriesDialog(this, userEmail, calories)
        }

        showImage(pic)
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