package com.example.dequote

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dequote.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    private val TAG = "TestActivity"

    private val mImageUrls: ArrayList<String> = ArrayList()
    private val mNames: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initImageBitmaps()
        binding.rvTest.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
            adapter = TestAdapter(mImageUrls,mNames)

        }
    }

    private fun initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.")
        mImageUrls.add("https://openxcell-development-public.s3.ap-south-1.amazonaws.com/arira/product_images/1605532853_5fb27cb55954b.jpg")
        mNames.add("Trondheim")
        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg")
        mNames.add("Rocky Mountain National Park")
        mImageUrls.add("https://openxcell-development-public.s3.ap-south-1.amazonaws.com/arira/product_images/1605532610_5fb27bc263743.jpeg")
        mNames.add("Portugal")
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg")
        mNames.add("Mahahual")
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg")
        mNames.add("Portugal")
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg")
        mNames.add("White Sands Desert")
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg")
        mNames.add("Portugal")
        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg")
        mNames.add("Washington")
    }
}