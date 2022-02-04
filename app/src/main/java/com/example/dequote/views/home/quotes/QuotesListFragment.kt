package com.example.dequote.views.home.quotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentQuotesListBinding
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.HomeViewModel
import com.example.dequote.views.home.quotes.adapter.QuotesListAdapter
import com.example.dequote.views.home.quotes.adapter.QuotesLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuotesListFragment :
    BaseFragment<FragmentQuotesListBinding>(FragmentQuotesListBinding::inflate) {

    lateinit var quotesListAdapter: QuotesListAdapter

    private val TAG = "QuotesListFragment"

    private val homeViewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        lifecycleScope.launch {
            homeViewModel.getQuotesList().collect {
                quotesListAdapter.submitData(it)
            }
        }

    }

    private fun setupList() {
        binding.rvQuotes.layoutManager = LinearLayoutManager(requireContext())

        quotesListAdapter = QuotesListAdapter() {
            requireContext().showToast("Added to Favorites")
            homeViewModel.updateFavoriteQuoteInDB(true, it._id)
            homeViewModel.insertFavQuote(
                FavQuotes(
                    favQuotesId = it._id,
                    author = it.author,
                    content = it.content
                )
            )
        }

        binding.rvQuotes.adapter =
            quotesListAdapter.withLoadStateFooter(footer = QuotesLoadStateAdapter())
    }

}