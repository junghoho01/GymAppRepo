package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatGenderBinding

class WhatGenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWhatGenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = arrayOf("Male", "Female")

        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = MySpinnerItemSelectedListener()

        binding.btnSubmit.setOnClickListener {
            toAgeActivity()
        }
    }

    private fun toAgeActivity() {
        val intent = Intent(this@WhatGenderActivity, WhatAge::class.java)
        startActivity(intent)
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatGenderActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}