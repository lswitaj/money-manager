package com.lswitaj.portfelmanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SymbolsDatabaseDao {
    //TODO(implement remove)
    //TODO(implement update)

    @Insert
    fun insert(symbol: SymbolsOverview)
    //TODO(change the insert to be suspended)
    //suspend fun insert(symbol: String)

    @Query("SELECT symbol_name from all_symbols_in_a_wallet ORDER BY symbolId DESC")
    fun getAllSymbols(): LiveData<List<String>>
}