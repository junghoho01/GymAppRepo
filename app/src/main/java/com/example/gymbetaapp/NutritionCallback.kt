package com.example.gymbetaapp

interface NutritionCallback {
    fun onSuccess(response: NutritionResponse?)
    fun onError(error: String)
}