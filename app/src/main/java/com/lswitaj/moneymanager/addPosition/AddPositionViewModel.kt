package com.lswitaj.moneymanager.addPosition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao
import com.lswitaj.moneymanager.search.NO_INTERNET_WHEN_ADDING_MESSAGE
import com.lswitaj.moneymanager.utils.parseErrorFormatter
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO(to change positionName to position here)
class AddPositionViewModel(
    val database: PositionsDatabaseDao,
    positionToAdd: Position
) : ViewModel() {
    //TODO(add double -> string formatter)
    private val _position = MutableLiveData<Position>()
    val position: LiveData<Position>
        get() = _position

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        _position.value = positionToAdd
    }

    //TODO(add validation similar to the SignUp)
    fun onAddPositionButtonClicked() {
        //TODO(symbol should be read somewhere here)
        viewModelScope.launch {
            //TODO(to change price to double)
            try {
                val position = Position(_position.value!!)
                val readyPositionErrorMessage = position.isReady()
                if(readyPositionErrorMessage.isNotBlank()) {
                    _errorMessage.value =  readyPositionErrorMessage
                    return@launch
                }
                database.addPosition(position)
                addNewPositionToBackend()
                _navigateToSummary.value = true
            } catch (e: Exception) {
                if (e.message!!.contains("resolve host")) {
                    _errorMessage.value = NO_INTERNET_WHEN_ADDING_MESSAGE
                } else {
                    _errorMessage.value = e.message
                }
            }
        }
    }

    private suspend fun addNewPositionToBackend() {
        withContext(Dispatchers.IO) {
            val parsePosition = ParseObject("Position")
            database.getLastPosition().let {
                //TODO(to extract all put expressions to the class)
                parsePosition.put("positionName", it.positionName)
                parsePosition.put("buyPrice", it.buyPrice)
                parsePosition.put("quantity", it.quantity)
                parsePosition.acl = ParseACL(ParseUser.getCurrentUser())
            }
            parsePosition.saveInBackground { e ->
                if (e != null) {
                    _errorMessage.value = parseErrorFormatter(e)
                }
            }
        }
    }

    fun addNewPositionComplete() {
        _navigateToSummary.value = false
        //TODO(scroll down when adding a new position to see it)
    }
}