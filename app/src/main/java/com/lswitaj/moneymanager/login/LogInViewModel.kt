package com.lswitaj.moneymanager.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogInViewModel() : ViewModel() {

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    fun onLogInButtonClicked() {
        _navigateToSummary.value = true
    }

    fun onNavigatedToSummary() {
        _navigateToSummary.value = false
    }
}