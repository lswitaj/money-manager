package com.lswitaj.portfelmanager.summary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.portfelmanager.network.AplhaVantageApi
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    //TODO(move init block and getAppleQuote() here)
    init {
        getAppleQuote()
    }

    private fun getAppleQuote() {
        lateinit var response: String
        viewModelScope.launch {
            try {
                response = AplhaVantageApi.aplhavantage.getQuote().toString()
            } catch (e: Exception) {
                response = e.toString()
            }
            Log.i("json", response)
        }
    }
}