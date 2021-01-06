package com.lswitaj.moneymanager.addPosition

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao
import com.lswitaj.moneymanager.data.network.Symbol
import com.lswitaj.moneymanager.search.NO_INTERNET_WHEN_ADDING_MESSAGE
import com.lswitaj.moneymanager.utils.getLastClosePrice
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
    __positionName: String
) : ViewModel() {
    private val _positionName = MutableLiveData<String>()
    val positionName: LiveData<String>
        get() = _positionName

    //TODO(add double -> string formatter)
    val buyPrice = MutableLiveData("")
    val quantity = MutableLiveData("")

    private val _currentPrice = MutableLiveData<String>()
    val currentPrice: LiveData<String>
        get() = _currentPrice

    private val _navigateToSummary = MutableLiveData<Boolean>()
    val navigateToSummary: LiveData<Boolean>
        get() = _navigateToSummary

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        _positionName.value = __positionName
        viewModelScope.launch {
            _currentPrice.value = getLastClosePrice(_positionName.value!!).toBigDecimal().toPlainString()
        }
    }

    fun onAddPositionButtonClicked() {
        //TODO(symbol should be read somewhere here)
        viewModelScope.launch {
            //TODO(to change price to double)
            try {
                database.addPosition(
                    Position(
                        _positionName.value!!,
                        buyPrice.value!!,
                        quantity.value!!,
                        _currentPrice.value!!
                    )
                )
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
                parsePosition.put("positionId", it.positionId)
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