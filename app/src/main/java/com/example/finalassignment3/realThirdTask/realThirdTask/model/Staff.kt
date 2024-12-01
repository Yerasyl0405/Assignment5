package com.example.finalassignment3.realThirdTask.realThirdTask.model

import com.google.gson.annotations.SerializedName
data class Staff(
    val staffId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String,
    val professionKey: String
)