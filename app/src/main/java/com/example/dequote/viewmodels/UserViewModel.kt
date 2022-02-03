package com.example.dequote.viewmodels

import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dequote.local.dao.UserDao
import com.example.dequote.local.entites.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {

    private val TAG = "LoginViewModel"

    private var _isValidUser = MutableLiveData<Boolean>()

    fun isValidUser(): LiveData<Boolean> = _isValidUser

    fun validateUser(userEmail: String, userPassword: String) {

        viewModelScope.launch {
            val isValidUser = userDao.checkValidCredentialsOrNot(userEmail, userPassword)
            Log.d(TAG, "validateUser: $isValidUser")
            _isValidUser.postValue(isValidUser)
        }

    }

    private var _ifUserExistsInDb = MutableLiveData<Boolean>()

    fun checkIfUserExists(): LiveData<Boolean> = _ifUserExistsInDb

    fun insertNewUser(userName: String, userEmail: String, userPassword: String) {

        viewModelScope.launch {

            val isUserExists = userDao.checkIfUserExists(userEmail)

            if (!isUserExists) {
                userDao.insertNewUser(
                    User(
                        userName = userName,
                        userEmail = userEmail,
                        userPassword = userPassword
                    )
                )
                Log.d(TAG, "insertNewUser: User Added")
            }

            _ifUserExistsInDb.postValue(isUserExists)

        }

    }

    val userNameErrorText = MutableLiveData<String>("")
    val userEmailErrorText = MutableLiveData<String>("")
    val userPasswordErrorText = MutableLiveData<String>("")

    fun afterTextChangedOnUsername(text: Editable?) {
        when {
            text!!.isEmpty() -> {
                userNameErrorText.postValue("Username is required")
            }
            text.length < 2 -> {
                userNameErrorText.postValue("Username can't be less than 2 characters")
            }
            else -> {
                userNameErrorText.postValue("")
            }
        }
    }

    fun afterTextChangedOnEmail(text: Editable?) {
        when {
            text!!.isEmpty() -> {
                userEmailErrorText.postValue("Email is required")
            }
            else -> {
                val isValid = Patterns.EMAIL_ADDRESS.matcher(text).matches()
                if (!isValid) {
                    userEmailErrorText.postValue("Invalid Email address, ex: abc@example.com")

                } else {
                    userEmailErrorText.postValue("")
                }
            }
        }
    }

    fun afterTextChangedOnPassword(text: Editable?) {
        when {
            text!!.isEmpty() -> {
                userPasswordErrorText.postValue("Password is required")
            }
            text.length < 8 -> {
                userPasswordErrorText.postValue("Password can't be less than 8 digit")
            }
            else -> {
                userPasswordErrorText.postValue("")
            }
        }
    }


}