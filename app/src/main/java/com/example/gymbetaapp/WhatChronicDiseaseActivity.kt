package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatChronicDiseaseBinding

class WhatChronicDiseaseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWhatChronicDiseaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatChronicDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val age = intent.getStringExtra("AGE_KEY") // For string data
        val height = intent.getStringExtra("HEIGHT_KEY") // For string data
        val weight = intent.getStringExtra("WEIGHT_KEY") // For string data

        val items = arrayOf(
            "None",
            "Kidney Disease",
            "Respiratory Disease",
            "Heart Disease",
            "Diabetes"
        )

        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = MySpinnerItemSelectedListener()

        binding.btnSubmit.setOnClickListener {
            addToFirebase(age, height, weight)
        }
    }

    private fun addToFirebase(age: String?, height: String?, weight: String?) {

        // Chceck if value isEmpty()
        if(age == null || height == null  || weight == null || binding.spinner.selectedItem.toString().isEmpty()){
            DialogUtils.denyDialog(this, "Please complete all choices")
        } else {

            val updateMap = mapOf(
                "age" to age,
                "height" to height,
                "weight" to weight,
                "chronicDisease" to binding.spinner.selectedItem.toString()
            )

            // need email...

        }

    }

//    private fun toMainActivity(age: String?, height: String?, weight: String?) {
//        val intent = Intent(this@WhatChronicDiseaseActivity, MainActivity::class.java)
//        intent.putExtra("AGE_KEY", age)
//        intent.putExtra("HEIGHT_KEY", height)
//        intent.putExtra("WEIGHT_KEY", weight)
//        intent.putExtra("DISEASE_KEY", binding.spinner.selectedItem.toString())
//        startActivity(intent)
//    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatChronicDiseaseActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}