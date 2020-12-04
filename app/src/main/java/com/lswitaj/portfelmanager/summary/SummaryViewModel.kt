package com.lswitaj.portfelmanager.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao
import com.lswitaj.portfelmanager.database.SymbolsOverview
import com.lswitaj.portfelmanager.getCurrentTimestamp
import com.lswitaj.portfelmanager.getYesterdayTimestamp
import com.lswitaj.portfelmanager.network.FinnhubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO(to be considered refreshing prices on the launching app)
class SummaryViewModel(
    val database: SymbolsDatabaseDao
) : ViewModel() {

    var allSymbols: LiveData<List<SymbolsOverview>> = database.getAllSymbols()

    init {
        viewModelScope.launch {
            updatePrices()
        }
    }

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

    //TODO(to add a price before adding a new symbol to the DB)
    //TODO(proper error handling to be added as it's the network fun)
    //getting positions and updating their prices
    suspend fun updatePrices() {
        withContext(Dispatchers.IO) {
            // new positions will be updated first thanks to reversing the list
            val allPositions = database.getAllSymbolsNames().reversed()
            allPositions.forEach { symbolName ->
                database.updatePrice(symbolName, getLastClosePrice(symbolName))
            }
        }
        allSymbols = database.getAllSymbols()
    }

    suspend fun getLastClosePrice(symbolName: String): Double = FinnhubApi.finnhub.getCandles(
            symbolName,
            getYesterdayTimestamp(),
            getCurrentTimestamp()
        ).closePrice.last()
}