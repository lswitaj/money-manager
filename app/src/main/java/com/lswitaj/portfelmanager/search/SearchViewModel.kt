package com.lswitaj.portfelmanager.search

import android.util.Log
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.QuoteProperty
import com.lswitaj.portfelmanager.network.SymbolMatches
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
//    TODO(change _searchableOperaResponse to Int again)
//    private val _searchableOperaResponse = MutableLiveData<List<SymbolMatches>>()
//    val reponse: LiveData<List<SymbolMatches>>
//        get() = _searchableOperaResponse
    private val _searchableOperaResponse = MutableLiveData<Int>()
    val searchableOperaResponse: LiveData<Int>
        get() = _searchableOperaResponse

    init {
        getSearchableForOpera()
    }

    private fun getSearchableForOpera() {
        lateinit var response: List<SymbolMatches>
        viewModelScope.launch {
//           try {
            var result = AplhaVantageApi.aplhavantage.getSearchableItems()
            _searchableOperaResponse.value = result.bestMatches.size
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }
}