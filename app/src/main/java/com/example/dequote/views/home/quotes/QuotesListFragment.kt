package com.example.dequote.views.home.quotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dequote.R
import com.example.dequote.databinding.FragmentQuotesListBinding

class QuotesListFragment : Fragment() {

    private lateinit var binding: FragmentQuotesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesListBinding.inflate(layoutInflater)
        return binding.root
    }

}