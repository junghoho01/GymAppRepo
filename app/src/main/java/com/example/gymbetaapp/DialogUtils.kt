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
}