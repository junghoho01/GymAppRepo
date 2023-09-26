package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityRegisterBinding
import com.example.gymbetaapp.databinding.ActivityWhatAgeBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            var intent = Intent(this, WhatAge::class.java)
            startActivity(intent)

        //            validation()
        }
    }

    private fun validation() {
        var email = binding.etEmail.text.toString()
        var username = binding.etUsername.text.toString()
        var pass = binding.etPassword.text.toString()
        var cpass = binding.etConfirmPass.text.toString()

        if (email.isNotEmpty() && username.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty()) {
            // All fields are not empty
            if (pass == cpass) {

                if(isEmailValid(email)){
                    navigateConfirm(email, username, pass)
                }
                else{
                    DialogUtils.denyDialog(this, "Please provide proper email")
                }

            } else {
                // Password and confirm password do not match
                // Handle this case, e.g., show an error message
                DialogUtils.denyDialog(this, "Password must match")
            }
        } else {
            // At least one of the fields is empty
            // Handle this case, e.g., show an error message
            DialogUtils.denyDialog(this, "Please complete the form")
        }
    }

    private fun navigateConfirm(email: String, username: String, pass: String) {
        val intent = Intent(this, ConfirmationActivity::class.java)
        // Add data as extras to the intent
        intent.putExtra("EMAIL_KEY", email)
        intent.putExtra("USERNAME_KEY", username)
        intent.putExtra("PASSWORD_KEY", pass)

        startActivity(intent)
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
        return email.matches(emailRegex)
    }
}