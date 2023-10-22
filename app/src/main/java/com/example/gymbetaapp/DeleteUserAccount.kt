package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymbetaapp.databinding.ActivityDeleteUserAccountBinding
import com.google.firebase.firestore.*

class DeleteUserAccount : AppCompatActivity() {

    private lateinit var binding : ActivityDeleteUserAccountBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        userAdapter = UserAdapter(userArrayList)
        recyclerView.adapter = userAdapter

        EventChangeListener()

        userAdapter.setOnItemClickListener(object: UserAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var name = userArrayList[position].username
//                Toast.makeText(this@DeleteUserAccount, name, Toast.LENGTH_SHORT).show()
//                var intent = Intent(this@SearchRecipeActivity, RecipeActivity::class.java)
//                intent.putExtra("FOODNAME_KEY", name)
//                startActivity(intent)
            }

            override fun onDeleteClick(position: Int) {
                // Handle "Delete" button click
                val name = userArrayList[position].username
                val email = userArrayList[position].email
                // Implement the logic to delete the user account here
                // You can use the name or other data to identify and delete the user
                Toast.makeText(this@DeleteUserAccount, "Delete clicked for $email", Toast.LENGTH_SHORT).show()
                DialogUtils.deleteDialog(this@DeleteUserAccount, email.toString()){
                    // Refresh the activity
                    val intent = Intent(this@DeleteUserAccount, DeleteUserAccount::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("user")
            .whereEqualTo("whos", "user")
            .whereEqualTo("status", "1")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(User::class.java))
                    }
                }

                userAdapter.notifyDataSetChanged()
            }
        })
    }
}