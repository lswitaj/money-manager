package com.lswitaj.moneymanager.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao
import com.lswitaj.moneymanager.data.network.FinnhubApi
import com.lswitaj.moneymanager.data.network.Symbol
import com.lswitaj.moneymanager.utils.getLastClosePrice
import kotlinx.coroutines.launch

//TODO(export to string.xml and unify across the app to not invoke the same fun multiple times)
const val NO_INTERNET_MESSAGE = "There's a problem to connect with a server connection. " +
        "Please check your internet connection."
const val NO_INTERNET_WHEN_ADDING_MESSAGE = "Position adding failed. Please check " +
        "your internet connection."

class SearchViewModel(
    val database: PositionsDatabaseDao
) : ViewModel() {
    init {
        //TODO(add a retry button when the app is offline)
        getAllSymbols()
    }

    //TODO(to have all symbols stored in the app maybe in some background job)
    private var allSymbols = emptyList<Symbol>()
    lateinit var positionToBeAdded: Position

    private val _searchableQueryResponse = MutableLiveData<List<Symbol>>()
    val searchableQueryResponse: LiveData<List<Symbol>>
        get() = _searchableQueryResponse

    private val _navigateToAddPosition = MutableLiveData<Boolean>()
    val navigateToAddPosition: LiveData<Boolean>
        get() = _navigateToAddPosition

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private fun getAllSymbols() {
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
        _searchableQueryResponse.value = allSymbols
            .filter { it.description.contains(query, true) }
    }

    // position with only name and close price is send to the addPosition Fragment
    // buyPrice and quantity are 0.0
    fun onNavigateToAddPosition(symbol: Symbol) {
        viewModelScope.launch {
            positionToBeAdded = Position(
                positionName = symbol.symbol,
                lastClosePrice = getLastClosePrice(symbol.symbol).toBigDecimal().toPlainString()
            )
            _navigateToAddPosition.value = true
        }
    }

    fun onNavigatedToAddPosition() {
        _navigateToAddPosition.value = false
    }
}