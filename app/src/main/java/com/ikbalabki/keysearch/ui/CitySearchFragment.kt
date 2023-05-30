package com.ikbalabki.keysearch.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.ikbalabki.keysearch.data.City
import com.ikbalabki.keysearch.databinding.CitySearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CitySearchFragment : Fragment(), TextWatcher {
    @Inject
    lateinit var adapter: CityResultAdapter
    private lateinit var binding: CitySearchFragmentBinding
    private val viewModel: CitiesViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = CitySearchFragmentBinding.inflate(inflater,container,false)
        binding.resultsRecyclerView.adapter = adapter
        adapter.onResultTap = this::citySelected
        binding.inputSearchText.addTextChangedListener(this)
        return binding.root
    }

    private fun citySelected(city: City) {
        viewModel.setSelected(city)
        val action = CitySearchFragmentDirections.showOnMap()
        val navController = binding.root.findNavController()

        val destination = navController.currentDestination as FragmentNavigator.Destination
        if (javaClass.name == destination.className) {
            navController.navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, {
            when(it){
                is CitiesViewState.ShowList -> loadResults(it.cities)
                is CitiesViewState.ShowNoResult -> showNoResults()
                CitiesViewState.Loading -> showLoading()
            }
        })
    }

    private fun loadResults(cities: List<City>?) {
        binding.noResultTextView.visibility = View.GONE

        adapter.clear()
        cities?.let {
            adapter.setResults(cities)
        }
    }
    private fun showNoResults() {
        adapter.clear()
        binding.noResultTextView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        adapter.clear()
        //add loading indicator
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text != null && text.isNotEmpty()) {
            viewModel.getCities(text.toString())
        } else {
            adapter.clear()
            showNoResults()
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}