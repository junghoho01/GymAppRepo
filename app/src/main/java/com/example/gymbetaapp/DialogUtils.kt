package com.example.gymbetaapp
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.gymbetaapp.databinding.ActivityDetailWorkoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import java.util.Base64


object DialogUtils {

    private var previousDialog: Dialog? = null

    fun showCustomDialog(context: Context, name: String) {
        // Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)
        val text = dialog.findViewById<TextView>(R.id.employeeName)
        text.text = "Granted, $name"

        val btnClose = dialog.findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun showCustomDialogNav(context: Context, name: String) {
        // Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)
        val text = dialog.findViewById<TextView>(R.id.employeeName)
        text.text = "Granted, $name"

        val btnClose = dialog.findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun denyDialog(context: Context, title: String) {

        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_denied)
        val text = dialog.findViewById<TextView>(R.id.tv_title)
        text.text = title
        val btnClose = dialog.findViewById<Button>(R.id.btnDeniedClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun infoDialog(context: Context, title: String) {

        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_info)
        val text = dialog.findViewById<TextView>(R.id.tv_title)
        text.text = title
        val btnClose = dialog.findViewById<Button>(R.id.btnDeniedClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun videoDialog(context: Context, title: String) {

        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_video)

        //Start here
        val videoView = dialog.findViewById<VideoView>(R.id.videoView)
        val storageRef = FirebaseStorage.getInstance().reference.child("videos/$title.mp4")
        val localFile = File.createTempFile("tempFile", "mp4")

        storageRef.getFile(localFile).addOnSuccessListener {
            val videoPath = localFile.path
            videoView.setVideoPath(videoPath)
            videoView.start()
        } .addOnFailureListener {
            Toast.makeText(context, "Error retrieving video", Toast.LENGTH_SHORT).show()
        }
        //End here

        val btnClose = dialog.findViewById<Button>(R.id.btnDeniedClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun caloriesDialog(context: Context, email: String, caloriesToAdd: String) {

        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_calories)

        var db = Firebase.firestore
        val ref = db.collection("user").document(email)
        ref.get().addOnSuccessListener {
            if (it != null){
                // Get data
                var calories = it.data?.get("caloriesBurnt")?.toString().toString()

                val text = dialog.findViewById<TextView>(R.id.tv_title)
                val text2 = dialog.findViewById<TextView>(R.id.tv_infoCalories)
                val textContent = "Current Total " + calories + " kcal"
                text2.text = "Add " + caloriesToAdd + " Calories"
                text.text = textContent

                var btnAdd = dialog.findViewById<Button>(R.id.btnDeniedClose)
                btnAdd.setOnClickListener {
                    var newCalories = calories.toInt() + caloriesToAdd.toInt()
                    db.collection("user").document(email).update("caloriesBurnt", newCalories.toString())
                    dialog.dismiss()

                    //How to notify user still?
                    showCustomDialog(context, "Total burnt " + newCalories.toString() + " kcal")
                }
            }
        }.addOnFailureListener {
            val text = dialog.findViewById<TextView>(R.id.tv_title)
            val textContent = "Internal Error"
            text.text = textContent
        }

        val btnClose = dialog.findViewById<Button>(R.id.btnDeniedClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun editCaloriesDialog(context: Context, email: String, callback: () -> Unit) {
        var db = Firebase.firestore
        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_editcalories)
        val etCalories = dialog.findViewById<EditText>(R.id.et_calories)
        val btnEdit = dialog.findViewById<Button>(R.id.btnEditCalories)
        btnEdit.setOnClickListener {
            db.collection("user").document(email).update("caloriesBurnt", etCalories.text.toString())
            dialog.dismiss()

            callback()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun editGainsDialog(context: Context, email: String, callback: () -> Unit) {
        var db = Firebase.firestore
        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_editnutrition)
        val etCalories = dialog.findViewById<EditText>(R.id.et_calories)
        val etProtein = dialog.findViewById<EditText>(R.id.et_protein)
        val btnEdit = dialog.findViewById<Button>(R.id.btnEditCalories)
        btnEdit.setOnClickListener {

            val data = mapOf(
                "caloriesGained" to etCalories.text.toString(),
                "proteinGained" to etProtein.text.toString()
            )

            db.collection("user").document(email).update(data)

            dialog.dismiss()
            callback()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    fun deleteDialog(context: Context, email: String, callback: () -> Unit) {
        var db = Firebase.firestore
        //Dismiss the previous dialog if it exists
        previousDialog?.dismiss()

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_denied)
        val text = dialog.findViewById<TextView>(R.id.tv_title)
        text.text = "Are you sure you want to delete this account?"
        val btnDelete = dialog.findViewById<Button>(R.id.btnDeniedClose)
        btnDelete.setOnClickListener {

            val data = mapOf(
                "status" to "0"
            )

            db.collection("user").document(email).update(data)

            dialog.dismiss()
            callback()

            dialog.dismiss()
        }

        // Set the current dialog as the previous dialog
        previousDialog = dialog

        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(text: String): String {
        val secretKey = "ThisIsASecretKey"
        val initializationVector = "1234567890123456"

        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
            val ivSpec = IvParameterSpec(initializationVector.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encryptedBytes = cipher.doFinal(text.toByteArray())
            return Base64.getEncoder().encodeToString(encryptedBytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(encryptedText: String): String {
        val secretKey = "ThisIsASecretKey"
        val initializationVector = "1234567890123456"

        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
            val ivSpec = IvParameterSpec(initializationVector.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val encryptedBytes = Base64.getDecoder().decode(encryptedText)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}