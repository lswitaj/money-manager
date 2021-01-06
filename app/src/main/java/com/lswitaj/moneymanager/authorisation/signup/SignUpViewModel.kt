package com.lswitaj.moneymanager.authorisation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lswitaj.moneymanager.utils.*
import com.parse.ParseUser

//TODO(to move those strings to strings.xml - issue is that it has to be done properly by the View)
const val emailValidationError = "Email address should be in the following format: example@mail.com"
const val usernameValidationError = "Username should be at least 5 characters long"
const val passwordValidationError = "Password should contain 8 characters" +
        " (small letter, big letter and number)."


class SignUpViewModel() : ViewModel() {
    // Back4app API doesn't accept null username and password
    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val email = MutableLiveData("")

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun onSignUpButtonClicked() {
        var signUpReady = false
        val username = usernameFormatter(username.value)
        val password = password.value
        val email = emailFormatter(email.value)

        val user = ParseUser()

        user.username = username
        user.email = email
        user.setPassword(password)

        when {
            !emailValidator(user.email) -> _errorMessage.value = emailValidationError
            !usernameValidator(user.username) -> _errorMessage.value = usernameValidationError
            !passwordValidator(password) -> _errorMessage.value = passwordValidationError
            else -> signUpReady = true
        }

        if(signUpReady) {
            user.signUpInBackground { e ->
            if (e == null) {
                _navigateToSummary.value = true
            } else {
                _errorMessage.value = parseErrorFormatter(e)
            }}
        }
    }

    fun onNavigatedToSummary() {
        _navigateToSummary.value = false
    }
}