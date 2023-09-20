package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatChronicDiseaseBinding
import com.example.gymbetaapp.databinding.ActivityWhatWeightBinding

class WhatChronicDiseaseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWhatChronicDiseaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatChronicDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            toDiseaseActivity()
        }
    }

    private fun toDiseaseActivity() {
        val intent = Intent(this@WhatChronicDiseaseActivity, WhatChronicDiseaseActivity::class.java)
        startActivity(intent)
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatChronicDiseaseActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}