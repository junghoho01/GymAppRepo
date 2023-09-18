package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navigate to login
        binding.btnLogin.setOnClickListener {
            navigateLogin()
        }

        //Navigate to register
        binding.btnRegister.setOnClickListener {
            navigateRegister()
        }
    }

    private fun navigateRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}