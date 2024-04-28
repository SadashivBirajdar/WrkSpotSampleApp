package com.wrkspot.sampleapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrkspot.sampleapp.databinding.FragmentHomeBinding
import com.wrkspot.sampleapp.ui.BaseFragment
import com.wrkspot.sampleapp.ui.adapter.CountryListAdapter
import com.wrkspot.sampleapp.utils.Population
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), SearchView.OnQueryTextListener,
    FilterDialog.FilterClickListener {

    private lateinit var adapter: CountryListAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        Log.d("TAG", "onViewCreated")
        homeScreenViewModel.getCountries()
    }

    private fun initUI() {
        binding.apply {
            searchView.setOnQueryTextListener(this@HomeFragment)
            adapter = CountryListAdapter()
            recList.layoutManager = LinearLayoutManager(requireContext())
            recList.adapter = adapter

            filterView.setOnClickListener {
                val dialog = FilterDialog()
                dialog.setFilterClickListener(this@HomeFragment)
                parentFragmentManager.let { dialog.show(it, FilterDialog.TAG) }
            }
        }
    }

    private fun initObserver() {
        homeScreenViewModel.countryListData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HomeScreenViewModel.Result.Failure -> {
                    hideProgress()
                    showMessage(result.error)
                }

                HomeScreenViewModel.Result.Loading -> {
                    showProgress()
                }

                is HomeScreenViewModel.Result.Success -> {
                    hideProgress()
                    adapter.updateAdapterData(result.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onFilterCLicked(population: Population) {
        homeScreenViewModel.getFilteredCountries(population)
    }
}