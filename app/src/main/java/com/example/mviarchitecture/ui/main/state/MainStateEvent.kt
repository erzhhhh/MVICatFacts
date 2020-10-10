package com.example.mviarchitecture.ui.main.state

sealed class MainStateEvent {

    class GetFactsEvent : MainStateEvent()

    class GetRandomFactEvent : MainStateEvent()

    class NoneEvent : MainStateEvent()
}