package com.example.dequote.views.account.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentLoginBinding
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.UserViewModel
import com.example.dequote.views.home.HomeActivity
import com.example.dequote.utils.DeQuoteDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val TAG = "LoginFragment"

    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var deQuoteDataStore: DeQuoteDataStore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        userViewModel.isValidUser().observe(viewLifecycleOwner) { isValidUser ->

            Log.d(TAG, "validateUser: $isValidUser")
            when {
                isValidUser -> {

                    lifecycleScope.launch {
                        deQuoteDataStore.setEmail(binding.edtEmail.text.toString())
                        deQuoteDataStore.setPassword(binding.edtPassword.text.toString())
                        deQuoteDataStore.setIsLoggedIn(true)
                    }
                    binding.edtEmail.text?.clear()
                    binding.edtPassword.text?.clear()
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            HomeActivity::class.java
                        )
                    )
                    requireActivity().finish()
                }
                else -> {
                    requireActivity().showToast("Invalid credentials")
                }
            }

        }

        binding.apply {

            btnLogin.setOnClickListener {

                if (edtEmail.text!!.isNotEmpty() && edtPassword.text!!.length >= 8) {
                    tilEmail.error = ""
                    tilPassword.error = ""
                    userViewModel?.validateUser(
                        edtEmail.text.toString(),
                        edtPassword.text.toString()
                    )
                } else {
                    userViewModel?.afterTextChangedOnEmail(edtEmail.text!!)
                    userViewModel?.afterTextChangedOnPassword(edtPassword.text!!)
                }

            }

            tvCreateNewAccount.setOnClickListener {

                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                findNavController().navigate(action)

            }

        }

    }

}