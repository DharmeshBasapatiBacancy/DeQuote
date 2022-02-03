package com.example.dequote.views.home.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dequote.databinding.RowItemQuotesBinding
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.network.models.Quote

class FavQuotesAdapter(var favQuotes: List<FavQuotes>,
                       var onItemClick : (FavQuotes) -> Unit) :
    RecyclerView.Adapter<FavQuotesAdapter.ViewHolder>() {
    class ViewHolder(val rowItemQuotesBinding: RowItemQuotesBinding) :
        RecyclerView.ViewHolder(rowItemQuotesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(favQuotes[position]) {
                rowItemQuotesBinding.apply {
                    tvQuote.text = content
                    tvAuthor.text = author
                    itemView.setOnClickListener {
                        onItemClick(favQuotes[position])
                    }
                }
            }
        }

    }

    override fun getItemCount() = favQuotes.size

    fun updateFavQuotesList(data: List<FavQuotes>){
        favQuotes = data
        notifyDataSetChanged()
    }

}