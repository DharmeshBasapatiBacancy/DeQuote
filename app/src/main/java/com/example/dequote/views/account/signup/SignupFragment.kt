package com.example.dequote.views.account.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dequote.base.BaseFragment
import com.example.dequote.databinding.FragmentSignupBinding
import com.example.dequote.utils.showToast
import com.example.dequote.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val TAG = "SignupFragment"

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        userViewModel.checkIfUserExists().observe(viewLifecycleOwner) { isUserExist ->

            when {
                isUserExist -> {
                    requireContext().showToast("Email already exists.")
                }
                else -> {
                    requireContext().showToast("Account created successfully.")
                    findNavController().popBackStack()
                }
            }

        }

        binding.apply {

            btnSignup.setOnClickListener {

                if (edtUsername.text!!.length > 2 && edtEmail.text!!.isNotEmpty() && edtPassword.text!!.length >= 8) {
                    userViewModel?.insertNewUser(
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

    }
}

