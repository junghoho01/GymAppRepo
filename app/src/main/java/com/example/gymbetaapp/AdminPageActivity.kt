package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.gymbetaapp.databinding.ActivityAdminPageBinding

class AdminPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDeleteUserAccount.setOnClickListener {
            toDeleteUserAcc()
        }

        binding.btnViewMemberList.setOnClickListener {
            toViewMemberList()
        }
    }

    private fun toViewMemberList() {
        var intent = Intent(this, ViewSubscribtionMemberActivity::class.java)
        startActivity(intent)
    }

    private fun toDeleteUserAcc() {
        var intent = Intent(this, DeleteUserAccount::class.java)
        startActivity(intent)
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish() // Exit the app
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000) // Reset the flag after 2 seconds
        }
    }
}