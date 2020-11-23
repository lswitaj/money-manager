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
    suspend fun addSymbol(symbol: SymbolsOverview)

    @Query("SELECT symbol_name from all_symbols_in_a_wallet ORDER BY symbolId ASC")
    fun getAllSymbols(): LiveData<List<String>>
}