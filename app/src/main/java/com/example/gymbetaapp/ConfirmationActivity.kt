package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityConfirmationBinding
import java.util.Random

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent extras
        val email = intent.getStringExtra("EMAIL_KEY")
        val username = intent.getStringExtra("USERNAME_KEY")
        val pass = intent.getStringExtra("PASSWORD_KEY")

        var sendCode = runRandomCodeGenerator()
        runEmailCode(sendCode)
        binding.btnVerify.setOnClickListener {
            validateCode(sendCode)
        }
    }

    private fun validateCode(receiveCode: String) {
        var code = binding.etCode.text.toString()

        if(code.isNotEmpty()){

            // Check if the code same with the generated code
            if(code == receiveCode){
                toWhatGender()
            } else {
                DialogUtils.denyDialog(this, "Invalid Code")
            }

        } else {
            DialogUtils.denyDialog(this, "Please enter the code")
        }
    }

    private fun runEmailCode(sendCode: String) {
        // Run the email code here
    }

    private fun runRandomCodeGenerator(): String {
        val random = Random()
        val codeLength = 6
        val generatedCode = StringBuilder()

        repeat(codeLength) {
            val randomNumber = random.nextInt(10) // Generates a random number between 0 and 9
            generatedCode.append(randomNumber)
        }

        return generatedCode.toString()
    }

    private fun toWhatGender() {
        val intent = Intent(this, WhatGenderActivity::class.java)
        startActivity(intent)
    }
}