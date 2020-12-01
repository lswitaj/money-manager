package com.lswitaj.portfelmanager.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao
import com.lswitaj.portfelmanager.database.SymbolsOverview
import com.lswitaj.portfelmanager.network.FinnhubApi
import com.lswitaj.portfelmanager.network.Symbol
import kotlinx.coroutines.launch

class SearchViewModel(
    val database: SymbolsDatabaseDao) : ViewModel() {
    private lateinit var allSymbols: List<Symbol>

    private val _searchableQueryResponse = MutableLiveData<List<Symbol>>()
    val searchableQueryResponse: LiveData<List<Symbol>>
        get() = _searchableQueryResponse

    private val _navigateToSummary = MutableLiveData<Symbol>()
    val navigateToSummary: LiveData<Symbol>
        get() = _navigateToSummary

    fun getAllSymbols() {
        viewModelScope.launch {
//           try {
            val result = FinnhubApi.finnhub.getSymbolsFromExchange()

            //TODO(to be removed)
            //TODO(proper error handling to be added)
            val candles = FinnhubApi.finnhub.getCandles("AAPL")
            Log.w("candle", candles.closePrice[0].toString())

            allSymbols = result
            _searchableQueryResponse.value = allSymbols
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }

    fun searchSymbols(query: String) {
        _searchableQueryResponse.value = allSymbols.filter { it.description.contains(query, true)}
    }

    fun addNewSymbol(symbol: Symbol) {
        viewModelScope.launch {
            database.addSymbol(SymbolsOverview(symbol.symbol))
            _navigateToSummary.value = symbol
        }
    }

    fun addNewSymbolComplete() {
        _navigateToSummary.value = null
        //TODO(scroll down when adding a new symbol to see it)
    }
}