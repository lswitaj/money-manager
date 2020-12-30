package com.lswitaj.moneymanager.search

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
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO(export to string.xml and unify across the app to not invoke the same fun multiple times)
const val NO_INTERNET_MESSAGE = "There's a problem to connect with a server. Please check " +
        "your internet connection."
const val NO_INTERNET_WHEN_ADDING_MESSAGE = "Position adding failed. Please check " +
        "your internet connection."

class SearchViewModel(
    val database: SymbolsDatabaseDao
) : ViewModel() {
    init {
        //TODO(add a retry button when the app is offline)
        getAllSymbols()
    }

    //TODO(to have all symbols stored in the app maybe in some background job)
    private var allSymbols = emptyList<Symbol>()

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
            try {
                val result = FinnhubApi.finnhub.getSymbolsFromExchange()
                //symbols without description or with non-letters won't be shown
                allSymbols = result
                    .filter { it.description.isNotEmpty() }
                    .filter { !it.symbol.contains(regex = Regex("""=+|\^+|#+|-+""")) }

                _searchableQueryResponse.value = allSymbols
            } catch (e: Exception) {
                //TODO(create a dedicated error mapper)
                if (e.message!!.contains("resolve host")) {
                    _errorMessage.postValue(NO_INTERNET_MESSAGE)
                } else {
                    //TODO(to add this part to the final document as it describes some async stuff)
                    _errorMessage.postValue(e.message)
                }
            }
        }
    }

    fun searchSymbols(query: String) {
        _searchableQueryResponse.value = allSymbols.filter { it.description.contains(query, true) }
    }

    fun addNewSymbol(symbol: Symbol) {
        viewModelScope.launch {
            //TODO(to change price to double)
            try {
                database.addSymbol(
                    SymbolsOverview(
                        symbol.symbol,
                        getLastClosePrice(symbol.symbol).toBigDecimal().toPlainString()
                    )
                )
                addNewSymbolToBackend()
                _navigateToSummary.value = symbol
            } catch (e: Exception) {
                if(e.message!!.contains("resolve host")) {
                    _errorMessage.value = NO_INTERNET_WHEN_ADDING_MESSAGE
                } else {
                    _errorMessage.value = e.message
                }
            }
        }
    }

    suspend fun addNewSymbolToBackend() {
        withContext(Dispatchers.IO) {
            val symbolOverviewParse = ParseObject("SymbolOverviewParse")
            database.getLastSymbol().let {
                //TODO(to extract all put expressions to the class)
                symbolOverviewParse.put("symbolId", it.symbolId)
                symbolOverviewParse.put("symbolName", it.symbolName)
                symbolOverviewParse.acl = ParseACL(ParseUser.getCurrentUser())
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