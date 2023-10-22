package com.example.gymbetaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.gymbetaapp.databinding.ActivityNutritionAnalysisBinding
import kotlin.math.roundToInt

class NutritionAnalysisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNutritionAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnalyse.setOnClickListener {
            var ingredients = binding.etAnalyseContent.text.toString()
            analyseFood(ingredients)
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
        }

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
    }

    private fun analyseFood(ingredients: String) {
        val edamamApiClient = EdamamApiClient()
        edamamApiClient.fetchNutritionData(ingredients, object : NutritionCallback {
            override fun onSuccess(response: NutritionResponse?) {
                // Handle the successful response here
                if (response != null) {
                    println("Calories: ${response.calories}")
                    println("Total Nutrients: ${response.totalNutrients}")
                    println("Total Daily: ${response.totalDaily}")

                    binding.tvCalories.text = response.calories.toString()

                    // Access fat
                    val totalFat = response.totalNutrients["FAT"]
                    val dailyFat = response.totalDaily["FAT"]

                    // Access saturated fat and trans fat
                    val saturatedFat = response.totalNutrients["FASAT"]
                    val dailySaturatedFat = response.totalDaily["FASAT"]
                    val transFat = response.totalNutrients["FATRN"]
                    val dailyTransFat = response.totalDaily["FATRN"]

                    // Access cholesterol
                    val totalCholesterol = response.totalNutrients["CHOLE"]
                    val dailyValueCholesterol = response.totalDaily["CHOLE"]

                    // Access sodium
                    val sodium = response.totalNutrients["NA"]
                    val dailyValueSodium = response.totalDaily["NA"]

                    // Access Total Carbohydrate
                    val totalCarbohydrate = response.totalNutrients["CHOCDF"]
                    val dailyValueCarbohydrate = response.totalDaily["CHOCDF"]

                    // Access Dietary Fiber
                    val dietaryFiber = response.totalNutrients["FIBTG"]
                    val dailyValueFiber = response.totalDaily["FIBTG"]

                    // Access Total Sugars
                    val totalSugars = response.totalNutrients["SUGAR"]
                    val dailyValueSugars = response.totalDaily["SUGAR"]

                    // Access Includes - Added Sugars
                    val addedSugars = response.totalNutrients["SUGAR.added"]
                    val dailyValueAddedSugars = response.totalDaily["SUGAR.added"]

                    // Access Total Protein
                    val totalProtein = response.totalNutrients["PROCNT"]
                    val dailyProtein = response.totalDaily["PROCNT"]

                    // Access Vitamin D
                    val vitaminD = response.totalNutrients["VITD"]
                    val dailyVitaminD = response.totalDaily["VITD"]

                    // Access Calcium
                    val calcium = response.totalNutrients["CA"]
                    val dailyCalcium = response.totalDaily["CA"]

                    // Access Iron
                    val iron = response.totalNutrients["FE"]
                    val dailyIron = response.totalDaily["FE"]

                    // Access Potassium
                    val potassium = response.totalNutrients["K"]
                    val dailyPotassium = response.totalDaily["K"]

                    if (totalFat != null) {
                        binding.tvTotalFat.text = "Total Fat ${totalFat.quantity.roundToInt()} ${totalFat.unit}"
                        binding.tvTotalFatDailyValue.text = "${dailyFat?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (saturatedFat != null) {
                        binding.tvSaturatedFat.text = "Saturated Fat ${saturatedFat.quantity.roundToInt() } ${saturatedFat.unit}"
                        binding.tvSaturatedFatDailyValue.text = "${dailySaturatedFat?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (transFat != null) {
                        binding.tvTransFat.text = "Trans Fat ${transFat.quantity.roundToInt()} ${transFat.unit}"
                        binding.tvTransFatDailyValue.text = "${dailyTransFat?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (totalCholesterol != null) {
                        binding.tvTotalCholesterol.text = "Total Cholesterol ${totalCholesterol.quantity.roundToInt()} ${totalCholesterol.unit}"
                        binding.tvTotalCholesterolDailyValue.text = "${dailyValueCholesterol?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (sodium != null) {
                        binding.tvTotalSodium.text = "Sodium ${sodium.quantity.roundToInt()} ${sodium.unit}"
                        binding.tvTotalSodiumDailyValue.text = "${dailyValueSodium?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (totalCarbohydrate != null) {
                        binding.tvTotalCarbohydrate.text = "Total Carbohydrate ${totalCarbohydrate.quantity.roundToInt()} ${totalCarbohydrate.unit}"
                        binding.tvTotalCarbohydrateDailyValue.text = "${dailyValueCarbohydrate?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (dietaryFiber != null) {
                        binding.tvDietaryFiber.text = "Dietary Fiber ${dietaryFiber.quantity.roundToInt()} ${dietaryFiber.unit}"
                        binding.tvDietaryFiberDailyValue.text = "${dailyValueFiber?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (totalSugars != null) {
                        binding.tvTotalSugars.text = "Total Sugars ${totalSugars.quantity.roundToInt()} ${totalSugars.unit}"
                        binding.tvTotalSugarsDailyValue.text = "${dailyValueSugars?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (addedSugars != null) {
                        binding.tvAddedSugars.text = "Includes - Added Sugars ${addedSugars.quantity.roundToInt()} ${addedSugars.unit}"
                        binding.tvAddedSugarsDailyValue.text = "${dailyValueAddedSugars?.quantity?.roundToInt() ?: 0}%"
                    }

                    if (totalProtein != null) {
                        binding.tvTotalProtein.text = "Total Protein ${totalProtein.quantity.roundToInt()} ${totalProtein.unit}"
                    }

                    if (dailyProtein != null) {
                        binding.tvTotalProteinDailyValue.text = "${dailyProtein.quantity.roundToInt()}%"
                    }

                    if (vitaminD != null) {
                        binding.tvVitaminD.text = "Vitamin D ${vitaminD.quantity.roundToInt()} ${vitaminD.unit}"
                    }

                    if (dailyVitaminD != null) {
                        binding.tvVitaminDDailyValue.text = "${dailyVitaminD.quantity.roundToInt()}%"
                    }

                    if (calcium != null) {
                        binding.tvCalcium.text = "Calcium ${calcium.quantity.roundToInt()} ${calcium.unit}"
                    }

                    if (dailyCalcium != null) {
                        binding.tvCalciumDailyValue.text = "${dailyCalcium.quantity.roundToInt()}%"
                    }

                    if (iron != null) {
                        binding.tvIron.text = "Iron ${iron.quantity.roundToInt()} ${iron.unit}"
                    }

                    if (dailyIron != null) {
                        binding.tvIronDailyValue.text = "${dailyIron.quantity.roundToInt()}%"
                    }

                    if (potassium != null) {
                        binding.tvPotassium.text = "Potassium ${potassium.quantity.roundToInt()} ${potassium.unit}"
                    }

                    if (dailyPotassium != null) {
                        binding.tvPotassiumDailyValue.text = "${dailyPotassium.quantity.roundToInt()}%"
                    }

                    DialogUtils.showCustomDialog(this@NutritionAnalysisActivity, "Nutrition Generated.")
                }
            }

            override fun onError(error: String) {
                // Handle the error here
                println("Error: $error")
            }
        })
    }
}