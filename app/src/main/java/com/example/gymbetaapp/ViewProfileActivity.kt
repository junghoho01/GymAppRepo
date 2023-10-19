package com.example.gymbetaapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityViewProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewProfileBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For gender
        val items = arrayOf("Male", "Female")
        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinnerGender.adapter = adapter
        binding.spinnerHeight.onItemSelectedListener = MySpinnerItemSelectedListener()

        // For age
        // Create an array of numbers from "1" to "100"
        val itemsage = (1..100).map { it.toString() }.toTypedArray()
        val adapterage = ArrayAdapter<Any?>(this, R.layout.spinner_list, itemsage)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinnerAge.adapter = adapterage
        binding.spinnerAge.onItemSelectedListener = MySpinnerItemSelectedListener()

        // For height
        // Create an array of numbers from "1" to "100"
        val itemsHeight = (1..300).map { it.toString() }.toTypedArray()
        val adapterHeight = ArrayAdapter<Any?>(this, R.layout.spinner_list, itemsHeight)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinnerHeight.adapter = adapterHeight
        binding.spinnerHeight.onItemSelectedListener = MySpinnerItemSelectedListener()

        // For Weight
        // Create an array of numbers from "1" to "100"
        val itemsWeight = (1..500).map { it.toString() }.toTypedArray()
        val adapterWeight = ArrayAdapter<Any?>(this, R.layout.spinner_list, itemsWeight)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinnerWeight.adapter = adapterWeight
        binding.spinnerWeight.onItemSelectedListener = MySpinnerItemSelectedListener()

        getData()

        binding.btnSave.setOnClickListener {
            toSave()
        }
    }

    private fun toSave() {

        var username = binding.etUsername.text.toString()
        var password = binding.etPassword.text.toString()
        var age = binding.spinnerAge.selectedItem.toString()
        var height = binding.spinnerHeight.selectedItem.toString()
        var weight = binding.spinnerWeight.selectedItem.toString()
        var gender = binding.spinnerGender.selectedItem.toString()

        val userMap = mapOf(
            "username" to username,
            "pass" to password,
            "age" to age,
            "height" to height,
            "weight" to weight,
            "gender" to gender
        )

        val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null).toString()
        val userId = userEmail

        db.collection("user").document(userId).update(userMap)
    }

    private fun getData() {
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
                val email = it.data?.get("email").toString()
                val password = it.data?.get("pass").toString()
                val username = it.data?.get("username").toString()

                binding.etEmail.setText(email)
                binding.etUsername.setText(username)
                binding.etPassword.setText(password)

                val genderArray = arrayOf("Male", "Female")
                val genderPosition = genderArray.indexOf(gender)
                if (genderPosition != -1) {
                    binding.spinnerGender.setSelection(genderPosition)
                }

                val weightArray = (1..500).map { it.toString() }.toTypedArray() // Assuming this is your spinner data
                val weightPosition = weightArray.indexOf(weight)
                if (weightPosition != -1){
                    binding.spinnerWeight.setSelection(weightPosition)
                }

                val ageArray = (1..100).map { it.toString() }.toTypedArray()
                val agePosition = ageArray.indexOf(age)
                if (agePosition != -1){
                    binding.spinnerAge.setSelection(agePosition)
                }

                val heightArray = (1..300).map { it.toString() }.toTypedArray()
                val heightPosition = heightArray.indexOf(height)
                if (heightPosition != -1) {
                    binding.spinnerHeight.setSelection(heightPosition)
                }

            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}