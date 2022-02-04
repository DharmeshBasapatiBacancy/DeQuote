package com.example.dequote.views.home.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dequote.R
import com.example.dequote.databinding.RowItemQuotesBinding
import com.example.dequote.local.entites.Quote
import com.example.dequote.network.models.QuoteApi
import javax.inject.Inject

class QuotesListAdapter(var onItemClick: (Quote) -> Unit) :
    PagingDataAdapter<Quote, QuotesListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                oldItem == newItem
        }
    }

    class ViewHolder(private val rowItemQuotesBinding: RowItemQuotesBinding) :
        RecyclerView.ViewHolder(rowItemQuotesBinding.root) {
        fun bind(quoteApi: Quote, onItemClick: (Quote) -> Unit) = with(rowItemQuotesBinding) {
            tvQuote.text = " \" ${quoteApi.content} \" "
            tvAuthor.text = "- ${quoteApi.author}"
            imgFav.setImageResource(R.drawable.ic_icon_un_favorite)
            if (quoteApi.isFavorite) {
                imgFav.setImageResource(R.drawable.ic_icon_favorite)
            }
            imgFav.setOnClickListener {
                imgFav.setImageResource(R.drawable.ic_icon_favorite)
                onItemClick(quoteApi)
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