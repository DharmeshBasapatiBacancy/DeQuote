package com.example.dequote.views.account.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentLoginBinding
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.UserViewModel
import com.example.dequote.views.home.quotes.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val TAG = "LoginFragment"

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.isValidUser().observe(viewLifecycleOwner) { isValidUser ->

            Log.d(TAG, "validateUser: $isValidUser")
            when {
                isValidUser -> {
                    binding.edtEmail.text?.clear()
                    binding.edtPassword.text?.clear()
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            HomeActivity::class.java
                        )
                    )
                    requireActivity().showToast("Success")
                }
                else -> {
                    requireActivity().showToast("Invalid credentials")
                }
            }

        }

        binding.apply {

            btnLogin.setOnClickListener {

                when {
                    edtEmail.text!!.isEmpty() -> {
                        tilEmail.error = "Enter Email"
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches() -> {
                        tilEmail.error = "Enter Valid Email"
                    }
                    edtPassword.text!!.isEmpty() -> {
                        tilPassword.error = "Enter Password"
                    }
                    else -> {
                        tilEmail.error = ""
                        tilPassword.error = ""
                        userViewModel.validateUser(
                            edtEmail.text.toString(),
                            edtPassword.text.toString()
                        )
                    }
                }


            }

            tvCreateNewAccount.setOnClickListener {

                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                findNavController().navigate(action)

            }

        }

    }

}