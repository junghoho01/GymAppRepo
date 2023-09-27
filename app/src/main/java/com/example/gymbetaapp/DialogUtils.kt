package com.example.gymbetaapp
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import com.example.gymbetaapp.databinding.ActivityDetailWorkoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

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
        val storageRef = FirebaseStorage.getInstance().reference.child("videos/situpvid.mp4")
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
}