package com.example.dequote.views.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dequote.UserLoginActivity
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentMyProfileBinding
import com.example.dequote.utils.DeQuoteDataStore
import com.example.dequote.viewmodels.UserViewModel
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

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        userViewModel.userDetails().observe(requireActivity()) {

            binding.edtUsername.setText(it.userName)
            binding.edtEmail.setText(it.userEmail)
            binding.edtPassword.setText(it.userPassword)

        }

        lifecycleScope.launch {

            userViewModel.getUserDetails(deQuoteDataStore.email.first())

        }

        binding.btnUpdateProfile.setOnClickListener {

            binding.apply {

                if (edtUsername.text!!.length > 2 && edtEmail.text!!.isNotEmpty() && edtPassword.text!!.length >= 8) {
                    userViewModel?.updateUserDetails(
                        edtUsername.text.toString(),
                        edtEmail.text.toString(),
                        edtPassword.text.toString()
                    )
                } else {
                    userViewModel?.afterTextChangedOnUsername(edtUsername.text!!)
                    userViewModel?.afterTextChangedOnEmail(edtEmail.text!!)
                    userViewModel?.afterTextChangedOnPassword(edtPassword.text!!)
                }
            }


        }

        binding.btnLogout.setOnClickListener {

            lifecycleScope.launch {

                deQuoteDataStore.clear()
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