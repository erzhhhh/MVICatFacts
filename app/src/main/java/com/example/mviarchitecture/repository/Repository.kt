package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.api.RetrofitBuilder
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiEmptyResponse
import com.example.mviarchitecture.util.ApiErrorResponse
import com.example.mviarchitecture.util.ApiSuccessResponse
import com.example.mviarchitecture.util.DataState

object Repository {

    fun getFacts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getFacts()) { apiResponse ->

                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(
                                    message = null,
                                    data = MainViewState(
                                        facts = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }

                            is ApiEmptyResponse -> {
                                DataState.error(
                                    message = "HTTP 204. Returned nothing!"
                                )
                            }
                        }
                    }
                }
            }
    }

    fun getFactOfTheDay(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getFactOfTheDay()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(
                                    message = null,
                                    data = MainViewState(
                                        randomFact = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }

                            is ApiEmptyResponse -> {
                                DataState.error(
                                    message = "HTTP 204. Returned nothing!"
                                )
                            }
                        }
                    }
                }
            }
    }
}