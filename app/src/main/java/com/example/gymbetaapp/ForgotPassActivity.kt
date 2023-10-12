package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener{

            var email = binding.etEmail.text.toString()

            if(email.isNotEmpty()){
                toConfirmForgotPassword(email)
            } else {
                DialogUtils.denyDialog(this, "Please fill in the email")
            }

        }

    }

    private fun toConfirmForgotPassword(emailInput: String) {
        var intent = Intent(this, ConfirmForgotPassword::class.java)
        intent.putExtra("EMAIL_KEY", emailInput)
        startActivity(intent)
    }
}