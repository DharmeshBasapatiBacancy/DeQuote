package com.example.dequote.views.home.quotes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dequote.R
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentQuotesListBinding
import com.example.dequote.network.models.Quote
import com.example.dequote.utils.Status
import com.example.dequote.utils.hide
import com.example.dequote.utils.show
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.HomeViewModel
import com.example.dequote.views.home.quotes.adapter.QuotesListAdapter
import com.example.dequote.views.home.quotes.adapter.QuotesLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuotesListFragment :
    BaseFragment<FragmentQuotesListBinding>(FragmentQuotesListBinding::inflate) {

    private lateinit var quotesListAdapter: QuotesListAdapter
    private val TAG = "QuotesListFragment"

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        lifecycleScope.launch {
            homeViewModel.getQuotesList().collect {
                quotesListAdapter.submitData(it)
            }
        }


        /*homeViewModel.quotesList().observe(requireActivity()) {
            Log.d(TAG, "Response of Quotes List = $it")
            when (it.status) {

                Status.SUCCESS -> {
                    binding.rvQuotes.show()
                    binding.progressBar.hide()
                }
                Status.ERROR -> {
                    binding.rvQuotes.show()
                    binding.progressBar.hide()
                    requireContext().showToast(it.message.toString())
                }
                Status.LOADING -> {
                    binding.rvQuotes.hide()
                    binding.progressBar.show()
                }

            }
        }*/

    }

    private fun setupList() {
        binding.rvQuotes.layoutManager = LinearLayoutManager(requireContext())

        quotesListAdapter = QuotesListAdapter()

        binding.rvQuotes.adapter = quotesListAdapter.withLoadStateFooter(footer = QuotesLoadStateAdapter())
    }

}