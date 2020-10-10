package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import com.example.mviarchitecture.api.RetrofitBuilder
import com.example.mviarchitecture.model.Fact
import com.example.mviarchitecture.model.RandomFact
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiSuccessResponse
import com.example.mviarchitecture.util.DataState
import com.example.mviarchitecture.util.GenericApiResponse

object Repository {

    fun getFacts(): LiveData<DataState<MainViewState>> {

        return object : NetworkBoundResource<List<Fact>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Fact>>) {
                result.value = DataState.data(
                    message = null,
                    data = MainViewState(
                        facts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Fact>>> {
                return RetrofitBuilder.apiService.getFacts()
            }
        }.asLiveData()
    }

    fun getFactOfTheDay(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<RandomFact, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<RandomFact>) {
                result.value = DataState.data(
                    message = null,
                    data = MainViewState(
                        randomFact = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RandomFact>> {
                return RetrofitBuilder.apiService.getFactOfTheDay()
            }
        }.asLiveData()
    }
}