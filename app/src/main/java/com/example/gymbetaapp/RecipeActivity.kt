package com.example.gymbetaapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityRecipeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("FOODNAME_KEY") // For string data

//        binding.tvNutrition.text = """
//          PROTEIN          97 g      Cholesterol      111 mg
//          FAT                  155 g      Sodium             596 mg
//          CARB              138 g      Calcium              86 mg
//                                                    Magnesium        94 mg
//                                                    Potassium        912 mg
//                                                    Iron                          3 mg
//        """.trimIndent()
        setDisplay(name)
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

                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
}