package com.example.mviarchitecture.ui.main.state

import com.example.mviarchitecture.model.Fact
import com.example.mviarchitecture.model.RandomFact

data class MainViewState(
    var facts: List<Fact>? = null,
    var randomFact: RandomFact? = null
)