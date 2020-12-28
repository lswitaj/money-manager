package com.lswitaj.moneymanager.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.SymbolsDatabaseDao
import com.lswitaj.moneymanager.data.database.SymbolsOverview
import com.lswitaj.moneymanager.data.network.FinnhubApi
import com.lswitaj.moneymanager.data.network.Symbol
import com.lswitaj.moneymanager.utils.getLastClosePrice
import com.lswitaj.moneymanager.utils.parseErrorFormatter
import com.parse.ParseObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getAllSymbols() {
        viewModelScope.launch {
//           try {
            val result = FinnhubApi.finnhub.getSymbolsFromExchange()
            //symbols without description or with non-letters won't be shown
            allSymbols = result
                .filter { it.description.isNotEmpty() }
                .filter { !it.symbol.contains(regex = Regex("""=+|\^+|#+|-+""")) }

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
            //TODO(to change price to double)
            database.addSymbol(
                SymbolsOverview(
                    symbol.symbol,
                    getLastClosePrice(symbol.symbol).toBigDecimal().toPlainString()
                )
            )
            addNewSymbolToBackend()
            _navigateToSummary.value = symbol
        }
    }

    suspend fun addNewSymbolToBackend() {
        withContext(Dispatchers.IO) {
            val symbolOverviewParse = ParseObject("SymbolOverviewParse")
            database.getLastSymbol().let {
                //TODO(to extract all put expressions to the class)
                symbolOverviewParse.put("symbolId", it.symbolId)
                symbolOverviewParse.put("symbolName", it.symbolName)
                symbolOverviewParse.put("lastClosePrice", it.lastClosePrice)
            }

            symbolOverviewParse.saveInBackground { e ->
                if (e != null) {
                    _errorMessage.value = parseErrorFormatter(e)
                }
            }
        }
    }

    fun addNewSymbolComplete() {
        _navigateToSummary.value = null
        //TODO(scroll down when adding a new symbol to see it)
    }
}