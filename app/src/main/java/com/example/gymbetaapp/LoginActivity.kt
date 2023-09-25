package com.example.gymbetaapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvForgotPass.setOnClickListener {
            var intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        binding.tvRegisterAcc.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            toMainActivity()
//            validateAccess()
        }
    }

    private fun validateAccess() {
        var enusername = binding.etEmail.text.toString()
        var enpassword = binding.etPassword.text.toString()

        val userMap = hashMapOf(
            "name" to "hello"
        )

        val userId = "user1"

        val ref = db.collection("gym").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null){
                // Get data
                val username = it.data?.get("username")?.toString()
                val password = it.data?.get("password")?.toString()

                if (enusername == username && enpassword == password){
                    toMainActivity()
                }
                else {
                    DialogUtils.denyDialog(this, "Invalid Credential")
                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toMainActivity() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}