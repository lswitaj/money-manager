package com.lswitaj.portfelmanager.search

import android.util.Log
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.QuoteProperty
import com.lswitaj.portfelmanager.network.SymbolMatches
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchableOperaResponse = MutableLiveData<List<SymbolMatches>>()
    val searchableOperaResponse: LiveData<List<SymbolMatches>>
        get() = _searchableOperaResponse

    private val _searchPhrase = MutableLiveData<String>()
    val searchPhrase: LiveData<String>
        get() = _searchPhrase

    init {
        getSearchableForOpera()
    }

    private fun getSearchableForOpera() {
        lateinit var response: List<SymbolMatches>
        viewModelScope.launch {
//           try {
            var result = AplhaVantageApi.aplhavantage.getSearchableItems()
            _searchableOperaResponse.value = result.bestMatches
            //TODO(to be removed)
            //_searchableOperaResponse.value = result.bestMatches.size
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }
}