package com.lswitaj.portfelmanager.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao

class SummaryViewModel(
    val database: SymbolsDatabaseDao) : ViewModel() {

    val allSymbols = database.getAllSymbols()

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    //TODO(to add searching for quotes)
    //TODO(to add fancy animation to the FAB button, use fab.show() and fab.hide()
    // methods https://material.io/develop/android/components/floating-action-button#regular-fabs)
    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }
}