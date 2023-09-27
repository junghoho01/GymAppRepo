package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatAgeBinding

class WhatAge : AppCompatActivity() {
    private lateinit var binding : ActivityWhatAgeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val email = intent.getStringExtra("EMAIL_KEY") // For string data
        val gender = intent.getStringExtra("GENDER_KEY") // For string data

        // Create an array of numbers from "1" to "100"
        val items = (1..100).map { it.toString() }.toTypedArray()

        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = MySpinnerItemSelectedListener()

        binding.btnSubmit.setOnClickListener {
            toHeight(email, gender)
        }
    }

    private fun toHeight(email: String?, gender: String?) {
        val intent = Intent(this@WhatAge, WhatHeightActivity::class.java)
        intent.putExtra("AGE_KEY", binding.spinner.selectedItem.toString())
        intent.putExtra("EMAIL_KEY", email)
        intent.putExtra("GENDER_KEY", gender)
        startActivity(intent)
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatAge, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}