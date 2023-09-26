package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatWeightBinding

class WhatWeightActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWhatWeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val age = intent.getStringExtra("AGE_KEY") // For string data
        val height = intent.getStringExtra("HEIGHT_KEY") // For string data

        // Create an array of numbers from "1" to "100"
        val items = (1..500).map { it.toString() }.toTypedArray()

        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = MySpinnerItemSelectedListener()

        binding.btnSubmit.setOnClickListener {
            toDiseaseActivity(age, height)
        }
    }

    private fun toDiseaseActivity(age: String?, height: String?) {
        val intent = Intent(this@WhatWeightActivity, WhatChronicDiseaseActivity::class.java)
        intent.putExtra("AGE_KEY", age)
        intent.putExtra("HEIGHT_KEY", height)
        intent.putExtra("WEIGHT_KEY", binding.spinner.selectedItem.toString())
        startActivity(intent)
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatWeightActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }

}