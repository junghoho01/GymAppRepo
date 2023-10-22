package com.example.gymbetaapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityRecipeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("FOODNAME_KEY") // For string data

        setDisplay(name)

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

    private fun setDisplay(name: String?) {

        if(name!!.isNotEmpty()){
            val ref = db.collection("recipe").document(name)
            ref.get().addOnSuccessListener {
                if (it != null){
                    // Get data
                    val calcium = it.data?.get("calcium")?.toString()
                    val carb = it.data?.get("carb")?.toString()
                    val cholesterol = it.data?.get("cholesterol")?.toString()
                    val fat = it.data?.get("fat")?.toString()
                    val iron = it.data?.get("iron")?.toString()
                    val magnesium = it.data?.get("magnesium")?.toString()
                    val name = it.data?.get("name")?.toString()
                    val perServing = it.data?.get("perServing")?.toString()
                    val pictureName = it.data?.get("pictureName")?.toString()
                    val potassium = it.data?.get("potassium")?.toString()
                    val protein = it.data?.get("protein")?.toString()
                    val sodium = it.data?.get("sodium")?.toString()

                    // Food name
                    binding.tvRecipeTitle.text = name

                    // Per serving
                    val items = perServing?.split(";")
                    val formattedList = items?.joinToString("\n- ")
                    binding.tvServing.text = "- $formattedList"

                    // Nutrition
                    binding.tvProtein.text = protein + " g"
                    binding.tvFat.text = fat + " g"
                    binding.tvCarb.text = carb + " g"
                    binding.tvCholesterol.text = cholesterol + " mg"
                    binding.tvSodium.text = sodium  + " mg"
                    binding.tvCalcium.text = calcium  + " mg"
                    binding.tvMagnesium.text = magnesium  + " mg"
                    binding.tvPotassium.text = potassium  + " mg"
                    binding.tvIron.text = iron  + " mg"

                    showImage(pictureName)

                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showImage(pictureName: String?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageRef = FirebaseStorage.getInstance().reference.child("images/$pictureName.jpg")
        val localfile = File.createTempFile("tempFile", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {

            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.ivFoodImage.setImageBitmap(bitmap)
        }.addOnFailureListener {

            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }

            Toast.makeText(this, "Error Retrieve Image", Toast.LENGTH_SHORT).show()
        }
    }
}