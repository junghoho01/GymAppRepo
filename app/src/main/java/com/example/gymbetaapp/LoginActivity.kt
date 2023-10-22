package com.example.gymbetaapp

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
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
            //toMainActivity()
            validateAccess()
        }

        val intent = intent
        val flag = intent.getStringExtra("FLAG_KEY") // For string data
        if (flag == "1"){
            DialogUtils.showCustomDialog(this, "Please check your email for further step!  ")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateAccess() {
        var enemail = binding.etEmail.text.toString()
        var enpassword = binding.etPassword.text.toString()

        if (enemail.isEmpty() || enpassword.isEmpty()){
            DialogUtils.denyDialog(this, "Please fill in email or password!")
        } else {
            val ref = db.collection("user").document(enemail)
            ref.get().addOnSuccessListener {
                if (it != null){
                    // Get data
                    val email = it.data?.get("email")?.toString()
                    val password = it.data?.get("pass")?.toString()
                    val whos = it.data?.get("whos")?.toString()
                    val status = it.data?.get("status")?.toString()

                    val sharedPref = getSharedPreferences("my_app_session", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()

                    editor.putString("user_email", email)
                    editor.apply()

                    if (enemail == email && enpassword == DialogUtils.decrypt(password!!) && whos == "user" && status == "1"){
                        toMainActivity()
                    }
                    else if (enemail == email && enpassword == password && whos == "admin"){
                        toAdminPage()
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
    }

    private fun toAdminPage() {
        val intent = Intent(this@LoginActivity, AdminPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toMainActivity() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}