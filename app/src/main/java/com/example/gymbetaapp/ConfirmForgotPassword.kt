package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityConfirmForgotPasswordBinding

class ConfirmForgotPassword : AppCompatActivity() {

   private lateinit var binding : ActivityConfirmForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            toConfirmation()
        }
    }

    private fun toConfirmation() {
        val intent = Intent(this, ConfirmationActivity::class.java)
        startActivity(intent)
    }
}