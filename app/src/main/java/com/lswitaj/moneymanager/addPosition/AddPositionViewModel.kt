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
import java.lang.Double.parseDouble

const val MAX_NUMBER = 1000000000.0
const val positionExceedsMaximumError =
    "Buy price and quantity cannot exceed $MAX_NUMBER"
const val positionValidationError =
    "Buy price and quantity have to be non-negative decimal numbers."

//TODO(to change positionName to position here)
class AddPositionViewModel(
    val database: PositionsDatabaseDao,
    positionToAdd: Position
) : ViewModel() {
    private val _position = MutableLiveData<Position>()
    val position: LiveData<Position>
        get() = _position

    val buyPrice = MutableLiveData("0.0")
    val quantity = MutableLiveData("0.0")

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
            try {
                if (isPositionReady()) {
                    val position = Position(_position.value!!)
                    database.addPosition(position)
                    addNewPositionToBackend()
                    _navigateToSummary.value = true
                }
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

    private fun isPositionReady(): Boolean {
        if (parseDouble(buyPrice.value!!) >= MAX_NUMBER ||
            parseDouble(quantity.value!!) >= MAX_NUMBER
        ) {
            _errorMessage.value = positionExceedsMaximumError
            return false
        }
            try {
                _position.value?.buyPrice = parseDouble(buyPrice.value!!)
                _position.value?.quantity = parseDouble(quantity.value!!)
            } catch (e: Exception) {
                _errorMessage.value = positionValidationError
                return false
            }
        return true
    }
}