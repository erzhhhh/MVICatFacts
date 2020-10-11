package com.example.mviarchitecture.api

import androidx.lifecycle.LiveData
import com.example.mviarchitecture.model.Fact
import com.example.mviarchitecture.model.RandomFact
import com.example.mviarchitecture.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("facts/random")
    fun getFactOfTheDay(): LiveData<GenericApiResponse<RandomFact>>

    @GET("facts/random?animal_type=cat&amount=20")
    fun getFacts(): LiveData<GenericApiResponse<List<Fact>>>

}