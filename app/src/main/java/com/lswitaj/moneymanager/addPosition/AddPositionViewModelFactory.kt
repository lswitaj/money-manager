package com.lswitaj.moneymanager.addPosition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao

//TODO(to change positionName string to Position)
class AddPositionViewModelFactory(
    private val dataSource: PositionsDatabaseDao,
    private val position: Position
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPositionViewModel::class.java)) {
            return AddPositionViewModel(dataSource, position) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
