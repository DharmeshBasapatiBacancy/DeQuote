package com.example.dequote.views.home.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentFavQuotesListBinding
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.HomeViewModel
import com.example.dequote.views.home.favorite.adapter.FavQuotesAdapter
import com.example.dequote.views.home.quotes.adapter.QuotesListAdapter
import com.example.dequote.views.home.quotes.adapter.QuotesLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavQuotesListFragment :
    BaseFragment<FragmentFavQuotesListBinding>(FragmentFavQuotesListBinding::inflate) {

    private lateinit var favQuotesAdapter: FavQuotesAdapter
    private val TAG = "FavQuotesListFragment"

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        homeViewModel.getFavQuotesList()

        homeViewModel.favQuotesList().observe(requireActivity()) {
            favQuotesAdapter.updateFavQuotesList(it)
        }

    }

    private fun setupList() {
        binding.rvQuotes.layoutManager = LinearLayoutManager(requireContext())

        favQuotesAdapter = FavQuotesAdapter(arrayListOf()) {
            homeViewModel.removeFavQuote(it)
            requireContext().showToast("Removed")
        }

        binding.rvQuotes.adapter = favQuotesAdapter
    }


}