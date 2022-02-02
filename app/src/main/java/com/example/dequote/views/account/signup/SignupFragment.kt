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

        binding.edtUsername.addTextChangedListener(textWatcher)
        binding.edtEmail.addTextChangedListener(textWatcher)
        binding.edtPassword.addTextChangedListener(textWatcher)

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

                if (!validateUsername() || !validateEmail() || !validatePassword()) {
                    return@setOnClickListener
                }

                userViewModel.insertNewUser(
                    edtUsername.text.toString(),
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                )

            }

        }


    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            requireActivity().window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    fun validatePassword(): Boolean {
        when {
            binding.edtPassword.text.toString().trim().isEmpty() -> {
                binding.tilPassword.error = "Password is required"
                requestFocus(binding.edtPassword)
                return false
            }
            binding.edtPassword.text.toString().length < 6 -> {
                binding.tilPassword.error = "Password can't be less than 6 digit"
                requestFocus(binding.edtPassword)
                return false
            }
            else -> {
                binding.tilPassword.isErrorEnabled = false
            }
        }
        return true
    }

    fun validateUsername(): Boolean {
        when {
            binding.edtUsername.text.toString().trim().isEmpty() -> {
                binding.tilUsername.error = "Username is required"
                requestFocus(binding.edtUsername)
                return false
            }
            binding.edtUsername.text.toString().length < 2 -> {
                binding.tilUsername.error = "Username can't be less than 2 digit"
                requestFocus(binding.edtUsername)
                return false
            }
            else -> {
                binding.tilUsername.isErrorEnabled = false
            }
        }
        return true
    }

    fun validateEmail(): Boolean {

        when {
            binding.edtEmail.text.toString().trim().isEmpty() -> {
                binding.tilEmail.error = "Email is required"
                requestFocus(binding.edtEmail)
                return false
            }
            else -> {
                val emailId: String = binding.edtEmail.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
                if (!isValid) {
                    binding.tilEmail.error = "Invalid Email address, ex: abc@example.com"
                    requestFocus(binding.edtEmail)
                    return false
                } else {
                    binding.tilEmail.isErrorEnabled = false
                }
            }
        }
        return true

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            when (view?.id) {
                binding.edtUsername.id -> validateUsername()
                binding.edtEmail.id -> validateEmail()
                binding.edtPassword.id -> validatePassword()
            }
        }
    }

}

