package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymbetaapp.databinding.ActivityRegisterBinding
import com.example.gymbetaapp.databinding.ActivityWhatAgeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.atomic.AtomicBoolean

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            validation()
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

                    isEmailExist(email) { emailExists ->
                        if (emailExists) {
                            DialogUtils.denyDialog(this, "Email existed.")
                        } else {
                            navigateConfirm(email, username, pass)
                        }
                    }
                }
                else{
                    DialogUtils.denyDialog(this, "Wrong email format or email existed.")
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

    fun isEmailExist(email: String, callback: (Boolean) -> Unit) {
        val userId = email
        val ref = db.collection("user").document(userId)
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                // Email exists in the database
                callback(true)
            } else {
                // Email doesn't exist in the database
                callback(false)
            }
        }.addOnFailureListener { exception ->
            // Handle any errors here, and consider email as not existing in case of an error
            callback(false)
        }
    }

}