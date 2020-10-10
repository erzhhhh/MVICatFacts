package com.example.mviarchitecture.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fact(
    @Expose
    @SerializedName("createdAt")
    val createdAt: String? = null,

    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @Expose
    @SerializedName("text")
    val text: String? = null
)