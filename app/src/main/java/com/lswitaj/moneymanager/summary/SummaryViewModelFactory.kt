package com.lswitaj.portfelmanager.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lswitaj.portfelmanager.database.SymbolsDatabaseDao

class SummaryViewModelFactory(
    private val dataSource: SymbolsDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
            return SummaryViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
