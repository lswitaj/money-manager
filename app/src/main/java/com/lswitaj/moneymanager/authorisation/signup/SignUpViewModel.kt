package com.lswitaj.moneymanager.authorisation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lswitaj.moneymanager.parseErrorFormatter
import com.lswitaj.moneymanager.usernameFormatter
import com.parse.ParseUser

class SignUpViewModel() : ViewModel() {
    // Back4app API doesn't accept null username and password
    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val email = MutableLiveData("")

    private val _navigateToLogIn = MutableLiveData<Boolean>()
    val navigateToLogIn: LiveData<Boolean>
        get() = _navigateToLogIn

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun onSignUpButtonClicked() {
        val username = usernameFormatter(username.value)
        val password = password.value
        val email = email.value

        val user = ParseUser()

        user.username = username
        user.email = email
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                _errorMessage.value = "User registered"
                _navigateToLogIn.value = true
            } else {
                _errorMessage.value = parseErrorFormatter(e)
            }
        }
    }

    fun onNavigatedToLogIn() {
        _navigateToLogIn.value = false
    }
}