package com.lswitaj.moneymanager.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao
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
const val NO_INTERNET_MESSAGE = "There's a problem to connect with a server connection. " +
        "Please check your internet connection."

//TODO(to be moved to the main activity)
//const val LOGOUT_SUCCESS_MESSAGE = "Logout successful"
const val LOGOUT_FAILURE_MESSAGE = "Logout not successful. Please check your internet connection."
const val NO_RESULTS_ERROR_MESSAGE = "No results."
const val AUTH_ERROR_MESSAGE = "Authorisation error, please log in again."

//TODO(to be considered refreshing prices on the launching app)
class SummaryViewModel(
    val database: PositionsDatabaseDao
) : ViewModel() {
    val allPositions: LiveData<List<Position>> = database.getAllPositions()

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
        // if the user session doesn't exist redirect him to the log in screen
        if (ParseUser.getCurrentUser() != null) {
            viewModelScope.launch {
                refreshPositions()
                updatePrices()
            }
        } else {
            _errorMessage.value = AUTH_ERROR_MESSAGE
            _navigateToLogin.value = true
        }
    }

    //TODO(to add searching for quotes)
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
    //TODO(add refresh on refreshing the app - e.g. scroll the screen down and display the spinner)
    //TODO(proper error handling to be added as it's the network fun)
    // getting positions and updating their prices
    private suspend fun updatePrices() {
        withContext(Dispatchers.IO) {
            val allPositions = database.getAllPositionNames()

            allPositions.forEach { positionName ->
                try {
                    database.updatePrice(positionName, getLastClosePrice(positionName))
                } catch (e: Exception) {
                    //TODO(create a dedicated error mapper)
                    if (e.message!!.contains("resolve host")) {
                        _errorMessage.postValue(NO_INTERNET_MESSAGE)
                    } else {
                        _errorMessage.postValue(e.message)
                    }
                }
            }
        }
    }

    fun logOut() {
        try {
            //TODO(consider changing the logout logic to logOutInBackground)
            ParseUser.logOut()
            if (ParseUser.getCurrentUser() == null) {
                //TODO(to make some general error messages - maybe on the activity level - it'll be
                viewModelScope.launch {
                    database.clearDatabase()
                }
                _navigateToLogin.value = true
            }
        } catch (e: Exception) {
            _errorMessage.value = LOGOUT_FAILURE_MESSAGE
        }
    }

    private suspend fun refreshPositions() {
        withContext(Dispatchers.IO) {
            if (database.countPositions() != 0) {
                return@withContext
            } else {
                //TODO(not retrieve public objects, how to use ACL for this?)
                val query: ParseQuery<ParseObject> = ParseQuery.getQuery("Position")
                query.findInBackground { resultsList: MutableList<ParseObject>?, e: ParseException? ->
                    when {
                        resultsList == null -> _errorMessage.value = NO_RESULTS_ERROR_MESSAGE
                        e != null -> _errorMessage.value = parseErrorFormatter(e)
                        else -> viewModelScope.launch {
                            downloadDataFromServer(resultsList)
                        }
                    }
                }
            }
        }
    }

    //TODO(to add some spinner when this function is in progress)
    // add the symbol from the server db to the local db
    private suspend fun downloadDataFromServer(resultsList: MutableList<ParseObject>?) {
        viewModelScope.launch {
            resultsList?.forEach { result ->
                val positionName = result.getString("positionName")
                val buyPrice = result.getDouble("buyPrice")
                val quantity = result.getDouble("quantity")
                database.addPosition(
                    Position(
                        positionName,
                        buyPrice,
                        quantity,
                        getLastClosePrice(positionName)
                        //TODO(add profit ratio and currency)
                    )
                )
            }
        }
    }

    fun onPositionSwiped(position: Position) {
        viewModelScope.launch {
            database.removePosition(position.positionId)
        }
    }
}