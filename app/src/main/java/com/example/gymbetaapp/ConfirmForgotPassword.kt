package com.example.gymbetaapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gymbetaapp.databinding.ActivityConfirmForgotPasswordBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfirmForgotPassword : AppCompatActivity() {

    private lateinit var binding : ActivityConfirmForgotPasswordBinding
    private var db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val email = intent.getStringExtra("EMAIL_KEY") // For string data

        binding.btnSubmit.setOnClickListener {

            var randomPassword = generateRandomString(20)
            submitEmail(email.toString(), randomPassword)
            updatePassword(email.toString(), randomPassword)
            toLoginPage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePassword(email: String, randomPassword: String) {
        db.collection("user").document(email).update("pass", DialogUtils.encrypt(randomPassword))
    }

    private fun submitEmail(email: String, randomPassword: String) {
        // Run the email code here
        val senderEmail = "gymapp914@gmail.com"
        val password = "tdkhenzcbmburfzp"
//         Toast.makeText(this, randomPassword, Toast.LENGTH_SHORT).show()
        EmailForgotPasswordSender.sendEmail(senderEmail, email, password, randomPassword)
    }

    private fun toLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("FLAG_KEY", "1")
        startActivity(intent)
    }

    fun generateRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + "!@#$%^&*()_-+=<>?{}[]".toList()
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }}