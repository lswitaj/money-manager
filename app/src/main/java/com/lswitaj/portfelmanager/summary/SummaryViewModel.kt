package com.lswitaj.portfelmanager.summary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val reponse: LiveData<String>
        get() = _response

    init {
        getAppleQuote()
    }

    private fun getAppleQuote() {
        lateinit var response: String
        viewModelScope.launch {
//           try {
                var result = AplhaVantageApi.aplhavantage.getQuote()
//                //TODO(remove temp toString() method)
//                _response.value = "Success: ${result.toString()}"
            _response.value = "Success: ${result.toString()}"
//            } catch (e: Exception) {
//                _response.value = e.toString()
//            }
        }
    }
}