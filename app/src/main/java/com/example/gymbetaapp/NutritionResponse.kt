package com.example.gymbetaapp

data class NutritionResponse(
    val totalNutrients: Map<String, Nutrient>,
    val totalDaily: Map<String, Nutrient>,
    val calories: Double
)

data class Nutrient(
    val label: String,
    val quantity: Double,
    val unit: String
)
