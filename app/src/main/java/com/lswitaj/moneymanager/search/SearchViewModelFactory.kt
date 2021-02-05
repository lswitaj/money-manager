package com.lswitaj.moneymanager.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lswitaj.moneymanager.data.database.PositionsDatabaseDao

//TODO(to try to remove database from the searchViewModel and implement it on the activity level)
class SearchViewModelFactory(
    private val dataSource: PositionsDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
