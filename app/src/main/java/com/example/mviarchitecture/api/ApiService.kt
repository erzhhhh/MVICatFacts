package com.example.mviarchitecture.api

import com.example.mviarchitecture.model.Fact
import com.example.mviarchitecture.model.RandomFact
import retrofit2.http.GET

interface ApiService {

    @GET("/facts/random")
    fun getFactOfTheDay(): RandomFact

    @GET("/facts/random?animal_type=cat&amount=10")
    fun getFacts(): List<Fact>

}