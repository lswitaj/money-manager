package com.lswitaj.moneymanager.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lswitaj.moneymanager.parseErrorFormatter
import com.lswitaj.moneymanager.usernameFormatter
import com.parse.ParseUser

class LogInViewModel() : ViewModel() {

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    private val _navigateToSignUp = MutableLiveData<Boolean>()
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    // Back4app API doesn't accept null username and password
    val username = MutableLiveData("")
    val password = MutableLiveData("")

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun onLogInButtonClicked() {
        val username = usernameFormatter(username.value)
        val password = password.value

        ParseUser.logInInBackground(username, password) { user, e ->
            if (user != null) {
                //TODO(to display email/username in sidebar)
                //TODO(remove the line below if possible)
                _errorMessage.value = user.username + " " + user.email
                _navigateToSummary.value = true
            } else {
                _errorMessage.value = parseErrorFormatter(e)
            }
        }
    }

    fun onNavigatedToSummary() {
        _navigateToSummary.value = false
    }

    fun onSignUpButtonClicked() {
        _navigateToSignUp.value = true
    }

    fun onNavigatedToSignUp() {
        _navigateToSignUp.value = false
    }
}