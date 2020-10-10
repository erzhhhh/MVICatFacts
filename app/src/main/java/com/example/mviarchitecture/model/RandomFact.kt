package com.example.mviarchitecture.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RandomFact(
    @Expose
    @SerializedName("createdAt")
    val createdAt: String? = null,

    @Expose
    @SerializedName("verified")
    val verified: String? = null,

    @Expose
    @SerializedName("text")
    val text: String? = null
)