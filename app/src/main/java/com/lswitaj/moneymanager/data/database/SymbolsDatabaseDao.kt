package com.lswitaj.moneymanager.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SymbolsDatabaseDao {
    //TODO(implement remove)

    @Insert
    suspend fun addSymbol(symbol: SymbolsOverview)

    @Query("SELECT * from all_symbols_in_a_wallet ORDER BY symbolId ASC")
    fun getAllSymbols(): LiveData<List<SymbolsOverview>>

    @Query("SELECT symbol_name from all_symbols_in_a_wallet ORDER BY symbolId ASC")
    fun getAllSymbolsNames(): List<String>

    @Query("SELECT * from all_symbols_in_a_wallet ORDER BY symbolId DESC LIMIT 1")
    fun getLastSymbol(): SymbolsOverview

    @Query("UPDATE all_symbols_in_a_wallet SET last_close_price = :lastClosePrice WHERE (symbol_name = :symbolName AND last_close_price <> :lastClosePrice)")
    fun updatePrice(symbolName: String?, lastClosePrice: Double)
}