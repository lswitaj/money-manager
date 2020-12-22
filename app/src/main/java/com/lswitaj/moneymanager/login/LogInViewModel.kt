package com.lswitaj.moneymanager.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lswitaj.moneymanager.usernameModifier
import com.parse.ParseUser

class LogInViewModel() : ViewModel() {

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    // Back4app API doesn't accept null username and password
    val username = MutableLiveData("")
    val password = MutableLiveData("")

    fun onLogInButtonClicked() {
        //TODO(to add snackbar for exceptions from API)
        //TODO(login to be lowercased for both login and sing up)
        val username = usernameModifier(username.value)
        val password = password.value

        ParseUser.logInInBackground(username, password) { user, e ->
            if (user != null) {
                //TODO(to display email/username in sidebar)
                Log.w("response", user.username + " " + user.email)
                _navigateToSummary.value = true
            } else {
                Log.w("response", e.toString())
            }
        }
    }

    fun onNavigatedToSummary() {
        _navigateToSummary.value = false
    }
}