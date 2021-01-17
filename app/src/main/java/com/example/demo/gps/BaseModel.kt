package com.example.demo.gps

import com.google.gson.annotations.SerializedName


data class BaseModel<T>(
    @SerializedName("error_message")
    val errorMessage: String,
    @SerializedName("data")
    val data: T
)