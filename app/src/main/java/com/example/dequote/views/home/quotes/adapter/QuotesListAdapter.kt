package com.example.dequote.views.home.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dequote.databinding.RowItemQuotesBinding
import com.example.dequote.network.models.Quote

class QuotesListAdapter(var onItemClick : (Quote) -> Unit) :
    PagingDataAdapter<Quote, QuotesListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                oldItem.content == newItem.content

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                oldItem == newItem
        }
    }

    class ViewHolder(private val rowItemQuotesBinding: RowItemQuotesBinding) :
        RecyclerView.ViewHolder(rowItemQuotesBinding.root) {
        fun bind(quote: Quote, onItemClick : (Quote) -> Unit) = with(rowItemQuotesBinding) {
            tvQuote.text = quote.content
            tvAuthor.text = quote.author
            itemView.setOnClickListener {
                onItemClick(quote)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClick = onItemClick) }
    }

}