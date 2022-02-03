package com.example.dequote.views.home.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dequote.R
import com.example.dequote.databinding.FooterViewItemBinding

class QuotesLoadStateAdapter() :
    LoadStateAdapter<QuotesLoadStateAdapter.QuotesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: QuotesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): QuotesLoadStateViewHolder {
        return QuotesLoadStateViewHolder.create(parent)
    }

    class QuotesLoadStateViewHolder(
        private val binding: FooterViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            binding.progressBar.isVisible = loadState is LoadState.Loading

        }

        companion object {
            fun create(parent: ViewGroup): QuotesLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.footer_view_item, parent, false)
                val binding = FooterViewItemBinding.bind(view)
                return QuotesLoadStateViewHolder(binding)
            }
        }
    }
}
