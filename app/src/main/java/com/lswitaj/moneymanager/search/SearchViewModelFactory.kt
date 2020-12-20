package com.lswitaj.portfelmanager.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao

class SearchViewModelFactory(
    private val dataSource: SymbolsDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
