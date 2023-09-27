package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityWhatHeightBinding

class WhatHeightActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWhatHeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatHeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val age = intent.getStringExtra("AGE_KEY") // For string data
        val email = intent.getStringExtra("EMAIL_KEY") // For string data
        val gender = intent.getStringExtra("GENDER_KEY") // For string data

        // Create an array of numbers from "1" to "100"
        val items = (1..300).map { it.toString() }.toTypedArray()

        val adapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, items)
        adapter.setDropDownViewResource(R.layout.spinner_list) // Use your custom layout
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = MySpinnerItemSelectedListener()

        binding.btnSubmit.setOnClickListener {
            toWeight(age, email, gender)
        }
    }

    private fun toWeight(age: String?, email: String?, gender: String?) {
        val intent = Intent(this@WhatHeightActivity, WhatWeightActivity::class.java)
        intent.putExtra("AGE_KEY", age)
        intent.putExtra("EMAIL_KEY", email)
        intent.putExtra("GENDER_KEY", gender)
        intent.putExtra("HEIGHT_KEY", binding.spinner.selectedItem.toString())
        startActivity(intent)
    }

    inner class MySpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            Toast.makeText(this@WhatHeightActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}