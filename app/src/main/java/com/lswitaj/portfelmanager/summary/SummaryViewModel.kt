package com.lswitaj.portfelmanager.summary

import android.util.Log
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.QuoteProperty
import com.lswitaj.portfelmanager.network.SymbolMatches
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    private val _appleQuoteResponse = MutableLiveData<QuoteProperty>()
    val appleQuoteResponse: LiveData<QuoteProperty>
        get() = _appleQuoteResponse

    init {
        getAppleQuote()
    }

    private fun getAppleQuote() {
        lateinit var response: QuoteProperty
        viewModelScope.launch {
//           try {
            var result = AplhaVantageApi.aplhavantage.getQuote()
            _appleQuoteResponse.value = result.quoteProperty
//            } catch (e: Exception) {
//                //TODO(to be considered creating an error quoteProperty object)
//                // _response.value = e.toString()
//            }
        }
    }
}