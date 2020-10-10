package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.api.RetrofitBuilder
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiEmptyResponse
import com.example.mviarchitecture.util.ApiErrorResponse
import com.example.mviarchitecture.util.ApiSuccessResponse

object Repository {

    fun getFacts(): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getFacts()) { apiResponse ->

                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()

                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(
                                    facts = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                MainViewState() // handle errror
                            }

                            is ApiEmptyResponse -> {
                                MainViewState() // handle empty
                            }
                        }
                    }
                }
            }
    }

    fun getFactOfTheDay(): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getFactOfTheDay()) { apiResponse ->

                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()

                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(
                                    randomFact = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                MainViewState() // handle errror
                            }

                            is ApiEmptyResponse -> {
                                MainViewState() // handle empty
                            }
                        }
                    }
                }
            }
    }
}