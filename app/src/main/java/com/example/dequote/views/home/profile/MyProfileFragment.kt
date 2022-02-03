package com.example.dequote.views.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.dequote.UserLoginActivity
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentMyProfileBinding
import com.example.dequote.utils.DeQuoteDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val TAG = "MyProfileFragment"

    @Inject
    lateinit var deQuoteDataStore: DeQuoteDataStore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            binding.tvLabel.text = "Welcome ${deQuoteDataStore.email.first().toString()}"

        }

        binding.btnLogout.setOnClickListener {

            lifecycleScope.launch {

                deQuoteDataStore.setEmail("")
                deQuoteDataStore.setPassword("")
                deQuoteDataStore.setIsLoggedIn(false)
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        UserLoginActivity::class.java
                    )
                )
                requireActivity().finish()

            }

        }

    }

}