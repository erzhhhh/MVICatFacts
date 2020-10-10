package com.example.mviarchitecture.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecture.R
import com.example.mviarchitecture.ui.main.state.MainStateEvent

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        Log.i("MainFragment", "started")

        viewModel = activity?.let {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("activity is not MainActivity")

        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            // handle data
            dataState.data?.let { mainViewState ->
                mainViewState.randomFact?.let {
                    viewModel.setRandomFactData(it)
                }
                mainViewState.facts?.let {
                    viewModel.setFactsData(it)
                }
            }

            // handle error
            dataState.message?.let {

            }

            // handle loading
            dataState.loading.let {

            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.randomFact?.let { }
            viewState.facts?.let { }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.getFactOfTheDay -> {
                triggerGetFactOfTheDayEvent()
            }
            R.id.getfacts -> {
                triggerGetFactsEvent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetFactOfTheDayEvent() {
        viewModel.setStateEvent(MainStateEvent.GetRandomFactEvent())
    }

    private fun triggerGetFactsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetFactsEvent())
    }
}