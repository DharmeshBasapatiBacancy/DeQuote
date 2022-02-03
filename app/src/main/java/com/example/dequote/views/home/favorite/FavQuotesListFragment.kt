package com.example.dequote.views.home.favorite

import android.os.Bundle
import android.view.View
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentFavQuotesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavQuotesListFragment : BaseFragment<FragmentFavQuotesListBinding>(FragmentFavQuotesListBinding::inflate)  {

    private val TAG = "FavQuotesListFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}