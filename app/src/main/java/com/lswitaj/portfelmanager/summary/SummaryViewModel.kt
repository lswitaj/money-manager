package com.lswitaj.portfelmanager.summary

import android.util.Log
import androidx.lifecycle.*
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import com.lswitaj.portfelmanager.network.QuoteProperty
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    private val _response = MutableLiveData<QuoteProperty>()
    val reponse: LiveData<QuoteProperty>
        get() = _response

    init {
        getAppleQuote()
    }

    private fun getAppleQuote() {
        lateinit var response: QuoteProperty
        viewModelScope.launch {
//           try {
                var result = AplhaVantageApi.aplhavantage.getQuote()
//                //TODO(remove temp toString() method)
//                _response.value = "Success: ${result.toString()}"
            _response.value = result.quoteProperty
//            } catch (e: Exception) {
//                _response.value = e.toString()
//            }
        }
    }
}