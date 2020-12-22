package com.lswitaj.moneymanager.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parse.ParseUser

class LogInViewModel() : ViewModel() {

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLogInButtonClicked() {
        //TODO(to add snackbar when the username/password empty + for exceptions from API)
        //TODO(login to be lowercased for both login and sing up)
        ParseUser.logInInBackground(username.value, password.value) { user, e ->
            if (user != null) {
                Log.w("response", user.username)
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