package com.lswitaj.moneymanager.summary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.SymbolsDatabaseDao
import com.lswitaj.moneymanager.data.database.SymbolsOverview
import com.lswitaj.moneymanager.utils.getLastClosePrice
import com.lswitaj.moneymanager.utils.parseErrorFormatter
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO(export to string.xml)
const val NO_INTERNET_MESSAGE = "There's a problem to connect with a server. Please check " +
        "your internet connection."

//TODO(to be moved to the main activity)
//const val LOGOUT_SUCCESS_MESSAGE = "Logout successful"
const val LOGOUT_FAILURE_MESSAGE = "Logout not successful. Please check your internet connection."

//TODO(to be considered refreshing prices on the launching app)
class SummaryViewModel(
    val database: SymbolsDatabaseDao
) : ViewModel() {

    var allSymbols: LiveData<MutableList<SymbolsOverview>> = database.getAllSymbols()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    init {
        //TODO(to consider adding some special action if the user isNew == true,
        // e.g. walkthrough or welcome message)
        if (ParseUser.getCurrentUser() != null) {
            viewModelScope.launch {
                updatePrices()
            }
        } else {
            _navigateToLogin.value = true
        }
    }

    //TODO(to add searching for quotes)
    //TODO(to add fancy animation to the FAB button, use fab.show() and fab.hide()
    // methods https://material.io/develop/android/components/floating-action-button#regular-fabs)
    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }

    fun onNavigatedToLogin() {
        _navigateToLogin.value = false
    }

    //TODO(a global var that'll tell if the price updated is necessary)
    //TODO(to add a price before adding a new symbol to the DB)
    //TODO(proper error handling to be added as it's the network fun)
    //TODO(timeout handling when the symbol doesn't have candles anymore and also maybe removing
    // it before adding to the summary lists)
    // getting positions and updating their prices
    suspend fun updatePrices() {
        withContext(Dispatchers.IO) {
            val allPositions = database.getAllSymbolsNames()

            allPositions.forEach { symbolName ->
                try {
                    database.updatePrice(symbolName, getLastClosePrice(symbolName))
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
        allSymbols = database.getAllSymbols()
    }

    fun logOut() {
        try {
            //TODO(consider changing the logout logic to logOutInBackground)
            ParseUser.logOut()
            if (ParseUser.getCurrentUser() == null) {
                //TODO(to make some general error messages - maybe on the activity level - it'll be
                // not shown if the user is redirected to the login screen)
                //_errorMessage.value = LOGOUT_SUCCESS_MESSAGE
                _navigateToLogin.value = true
            }
        } catch (e: Exception) {
            _errorMessage.value = LOGOUT_FAILURE_MESSAGE
        }
    }

    fun refreshSymbols(downloadDataFromServer: Boolean) {
        if (downloadDataFromServer) {
            //TODO(not retrieve public objects)
            val query: ParseQuery<ParseObject> = ParseQuery.getQuery("SymbolOverviewParse")
            query.findInBackground { resultsList: MutableList<ParseObject>?, e: ParseException? ->
                when {
                    resultsList == null -> Log.w("result", "No results")
                    e == null -> Log.w("result", resultsList.toString())
                    else -> Log.w("result", parseErrorFormatter(e)!!)
                }
                //TODO(to add some spinner when this function is in progress)
                // add the symbol from the server db to the local db
                viewModelScope.launch {
                    resultsList?.forEach { result ->
                        val symbolName = result.get("symbolName").toString()
                        database.addSymbol(
                            SymbolsOverview(
                                symbolName,
                                getLastClosePrice(symbolName).toBigDecimal().toPlainString()
                            )
                        )
                    }
                }
            }
        }
    }
}