package com.example.gymbetaapp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EdamamApiClient {
    private val BASE_URL = "https://api.edamam.com/"
    private val APP_ID = "63a53b12"
    private val APP_KEY = "c90b628c2207ededeab4b335798e63e6"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: EdamamApiService = retrofit.create(EdamamApiService::class.java)

    fun fetchNutritionData(ingredient: String, callback: NutritionCallback) {
        val call = apiService.getNutritionData(APP_ID, APP_KEY, ingredient)

        call.enqueue(object : Callback<NutritionResponse> {
            override fun onResponse(
                call: Call<NutritionResponse>,
                response: Response<NutritionResponse>
            ) {
                if (response.isSuccessful) {
                    val nutritionResponse = response.body()
                    callback.onSuccess(nutritionResponse)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                callback.onError("Network request failed")
            }
        })
    }
}