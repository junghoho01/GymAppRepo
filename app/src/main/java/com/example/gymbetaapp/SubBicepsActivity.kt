package com.example.gymbetaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymbetaapp.databinding.ActivitySubBicepsBinding
import com.example.gymbetaapp.databinding.ActivitySubBrowseWorkoutsBinding
import com.google.firebase.firestore.*

class SubBicepsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var absArrayList: ArrayList<Abs>
    private lateinit var absAdapter: AbsAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var binding : ActivitySubBicepsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBicepsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // btmNavigation
        binding.navSection1.setOnClickListener {
            var intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection2.setOnClickListener {
            var intent = Intent(this, MealsAndNutritionTwoActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection3.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection4.setOnClickListener {
            var intent = Intent(this, RecommendationAndReportActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.navSection5.setOnClickListener {
            var intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

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
                var video = absArrayList[position].workoutVid
//                Toast.makeText(this@SubBrowseWorkoutsActivity, pic, Toast.LENGTH_SHORT).show()
                var intent = Intent(this@SubBicepsActivity, DetailWorkoutActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("info", info)
                intent.putExtra("pic", pic)
                intent.putExtra("CALORIES_KEY", calories)
                intent.putExtra("VIDEO_KEY", video)
                startActivity(intent)
            }

        })

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("bicepWorkout").
        addSnapshotListener(object: EventListener<QuerySnapshot> {
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