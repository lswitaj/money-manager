package com.lswitaj.portfelmanager.search

import android.util.Log
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.database.SymbolsDatabase
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao
import com.lswitaj.portfelmanager.database.SymbolsOverview
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.SymbolMatches
import kotlinx.coroutines.launch

class SearchViewModel(
    val database: SymbolsDatabaseDao) : ViewModel() {
    private val _searchableQueryResponse = MutableLiveData<List<SymbolMatches>>()
    val searchableQueryResponse: LiveData<List<SymbolMatches>>
        get() = _searchableQueryResponse

//    private val _searchPhrase = MutableLiveData<String>()
//    val searchPhrase: LiveData<String>
//        get() = _searchPhrase

    private val _navigateToSummary = MutableLiveData<SymbolMatches>()
    val navigateToSummary: LiveData<SymbolMatches>
        get() = _navigateToSummary

    fun searchSymbols(query: String?) {
        lateinit var response: List<SymbolMatches>
        viewModelScope.launch {
//           try {
            var result = AplhaVantageApi.aplhavantage.getSearchableItems(query)
            _searchableQueryResponse.value = result.bestMatches
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }

    fun addNewSymbol(symbol: SymbolMatches) {
        viewModelScope.launch {
            database.addSymbol(symbol = SymbolsOverview(symbol.symbol))
            _navigateToSummary.value = symbol
        }
    }

    fun addNewSymbolComplete() {
        _navigateToSummary.value = null
    }
}