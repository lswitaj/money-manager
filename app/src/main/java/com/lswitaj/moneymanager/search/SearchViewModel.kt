package com.lswitaj.moneymanager.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.SymbolsDatabaseDao
import com.lswitaj.moneymanager.data.database.SymbolsOverview
import com.lswitaj.moneymanager.data.network.FinnhubApi
import com.lswitaj.moneymanager.data.network.Symbol
import kotlinx.coroutines.launch

class SearchViewModel(
    val database: SymbolsDatabaseDao
) : ViewModel() {

    //TODO(to have all symbols stored in the app maybe in some background job)
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
            //symbols without description or with non-letters won't be shown
            allSymbols = result
                .filter { it.description.isNotEmpty() }
                .filter { !it.symbol.contains(regex= Regex("""=+|\^+|#+|-+""")) }

            _searchableQueryResponse.value = allSymbols
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }

    fun searchSymbols(query: String) {
        _searchableQueryResponse.value = allSymbols.filter { it.description.contains(query, true) }
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