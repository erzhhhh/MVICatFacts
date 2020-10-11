package com.example.mviarchitecture.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviarchitecture.R
import com.example.mviarchitecture.model.Fact
import com.example.mviarchitecture.ui.DataStateListener
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), FactsRecyclerViewAdapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel

    private lateinit var dataStateListener: DataStateListener

    private lateinit var factsRecyclerViewAdapter: FactsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            Log.i("MainFragment", "$context must implement DataStateListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        Log.i("MainFragment", "started")

        initRecyclerView()

        viewModel = activity?.let {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("activity is not MainActivity")

        subscribeObservers()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            factsRecyclerViewAdapter = FactsRecyclerViewAdapter(this@MainFragment)
            adapter = factsRecyclerViewAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // handle error and loading in mainActivity
            dataStateListener.onDataStateChange(dataState)

            // handle data
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let {
                    it.randomFact?.let {
                        viewModel.setRandomFactData(it)
                    }
                    it.facts?.let {
                        viewModel.setFactsData(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.randomFact?.let {
                randomFactTextView.text = it.text
            }
            viewState.facts?.let {
                factsRecyclerViewAdapter.submitList(it)
            }
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

    override fun onItemSelected(position: Int, item: Fact) {
        Toast.makeText(context, "Выполнен тап по факту", Toast.LENGTH_SHORT).show()
    }
}