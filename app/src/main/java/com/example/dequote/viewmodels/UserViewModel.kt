package com.example.dequote.viewmodels

import android.util.Log
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

    private var checkIsValidUser = MutableLiveData<Boolean>()

    fun isValidUser() : LiveData<Boolean> = checkIsValidUser

    private var _ifUserExistsInDb = MutableLiveData<Boolean>()

    fun checkIfUserExists(): LiveData<Boolean> = _ifUserExistsInDb

    fun validateUser(userEmail: String, userPassword: String) {

        viewModelScope.launch {
            val isValidUser = userDao.checkValidCredentialsOrNot(userEmail, userPassword)
            Log.d(TAG, "validateUser: $isValidUser")
            checkIsValidUser.postValue(isValidUser)
        }

    }

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

}