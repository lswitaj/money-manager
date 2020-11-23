package com.lswitaj.portfelmanager.summary

import android.util.Log
import android.widget.Button
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao
import com.lswitaj.portfelmanager.database.SymbolsOverview
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.QuoteProperty
import com.lswitaj.portfelmanager.network.SymbolMatches
import kotlinx.coroutines.launch

class SummaryViewModel(
    val database: SymbolsDatabaseDao) : ViewModel() {
//    private val _appleQuoteResponse = MutableLiveData<QuoteProperty>()
//    val appleQuoteResponse: LiveData<QuoteProperty>
//        get() = _appleQuoteResponse

    val allSymbols = database.getAllSymbols()

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    //TODO(to add searching for quotes)
//    private fun getSymbols() {
//        lateinit var response: QuoteProperty
//        viewModelScope.launch {
////           try {
//            //TODO(exchage hardcoded symbol with the parameter)
//            var result = AplhaVantageApi.aplhavantage.getQuote("AAPL")
//            _appleQuoteResponse.value = result.quoteProperty
////            } catch (e: Exception) {
////                //TODO(to be considered creating an error quoteProperty object)
////                // _response.value = e.toString()
////            }
//        }
//    }

    //TODO(to add fancy animation to the FAB button, use fab.show() and fab.hide()
    // methods https://material.io/develop/android/components/floating-action-button#regular-fabs)
    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }
}