package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gymbetaapp.databinding.ActivityConfirmationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConfirmationBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val email = intent.getStringExtra("EMAIL_KEY").toString()
        val username = intent.getStringExtra("USERNAME_KEY").toString()
        val pass = intent.getStringExtra("PASSWORD_KEY").toString()


        var sendCode = runRandomCodeGenerator()
        runEmailCode(sendCode, email)
        binding.btnVerify.setOnClickListener {
            validateCode(sendCode, email, username, pass)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateCode(receiveCode: String, email: String, username: String, pass: String) {
        var code = binding.etCode.text.toString()

        if(code.isNotEmpty()){

            // Check if the code same with the generated code
            if(code == receiveCode){
                toWhatGender(email, username, pass)
            } else {
                DialogUtils.denyDialog(this, "Invalid Code")
            }

        } else {
            DialogUtils.denyDialog(this, "Please enter the code")
        }
    }

    private fun runEmailCode(sendCode: String, receiverEmail: String?) {
        // Run the email code here
        val senderEmail = "gymapp914@gmail.com"
        val password = "tdkhenzcbmburfzp"
        Toast.makeText(this, receiverEmail, Toast.LENGTH_SHORT).show()
        EmailSender.sendEmail(senderEmail, receiverEmail, password, sendCode)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toWhatGender(email: String, username: String, pass: String) {
        insertDataToFirebase(email, username, pass)
        val intent = Intent(this, WhatGenderActivity::class.java)
        intent.putExtra("EMAIL_KEY", email)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertDataToFirebase(email: String, username: String, pass: String) {
        var db = Firebase.firestore

        val currentDate = Date()
        val formattedDate = formatDate(currentDate, "yyyy-MM-dd")

        val userMap = hashMapOf(
            "email" to email,
            "username" to username,
            "pass" to DialogUtils.encrypt(pass),
            "gender" to "",
            "age" to "",
            "height" to "",
            "weight" to "",
            "chronicDisease" to "",
            "status" to "1",
            "caloriesBurnt" to "0",
            "caloriesDate" to formattedDate,
            "caloriesGained" to "0",
            "proteinGained" to "0",
            "dateJoined" to formattedDate,
            "whos" to "user",
            "nutritionDate" to formattedDate

            // 1 represent true
        )

        val userId = email

        db.collection("user").document(userId).set(userMap)
            .addOnSuccessListener {
                // Success
            }
            .addOnFailureListener {
                // Fail
            }

    }

    fun formatDate(date: Date, format: String): String {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }
}