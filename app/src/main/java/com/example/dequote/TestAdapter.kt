package com.example.dequote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dequote.databinding.TestRowItemBinding

class TestAdapter(private val mImageUrls: ArrayList<String>, private val mNames: ArrayList<String>) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    inner class ViewHolder(val testRowItemBinding: TestRowItemBinding) :
        RecyclerView.ViewHolder(testRowItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        TestRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            testRowItemBinding.apply {
                tvProductName.text = mNames[position]
                Glide.with(itemView.context).load(mImageUrls[position])
                    .apply(
                        RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                    .into(imgProduct)
            }
        }
    }

    override fun getItemCount() = mImageUrls.size
}