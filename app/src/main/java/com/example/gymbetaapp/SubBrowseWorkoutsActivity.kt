package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class SubBrowseWorkoutsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var absArrayList: ArrayList<Abs>
    private lateinit var absAdapter: AbsAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_browse_workouts)

        recyclerView = findViewById(R.id.workoutRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        absArrayList = arrayListOf()
        absAdapter = AbsAdapter(absArrayList)
        recyclerView.adapter = absAdapter

        EventChangeListener()

        absAdapter.setOnItemClickListener(object: AbsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var title = absArrayList[position].title
                var info = absArrayList[position].workoutDesc
                var pic = absArrayList[position].workoutPic
                var calories = absArrayList[position].workoutCalories
//                Toast.makeText(this@SubBrowseWorkoutsActivity, pic, Toast.LENGTH_SHORT).show()
                var intent = Intent(this@SubBrowseWorkoutsActivity, DetailWorkoutActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("info", info)
                intent.putExtra("pic", pic)
                intent.putExtra("CALORIES_KEY", calories)
                startActivity(intent)
            }

        })
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("workouts").
                addSnapshotListener(object: EventListener<QuerySnapshot>{
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
                                absArrayList.add(dc.document.toObject(Abs::class.java))
                            }
                        }

                        absAdapter.notifyDataSetChanged()
                    }
                })
    }
}