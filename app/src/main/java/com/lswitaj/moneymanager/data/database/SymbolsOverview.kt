package com.lswitaj.moneymanager.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO(to adjust the word wallet, assets, symbols, etc.)
@Entity(tableName = "all_symbols_in_a_wallet")
data class SymbolsOverview constructor(
    @ColumnInfo(name = "symbol_name")
    var symbolName: String,

    @PrimaryKey(autoGenerate = true) var symbolId: Long = 0L,

    //TODO(add a timestamp so that the app knows if the symbol price should/not be updated)
    //TODO(to change the type to Double and add a text formatter)
    @ColumnInfo(name = "last_close_price") var lastClosePrice: String = "0.0"
)