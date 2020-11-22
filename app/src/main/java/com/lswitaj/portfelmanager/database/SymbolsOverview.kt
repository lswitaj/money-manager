package com.lswitaj.portfelmanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO(to adjust the word wallet, assets, symbols, etc.)
@Entity(tableName = "all_symbols_in_a_wallet")
data class SymbolsOverview constructor(
    @PrimaryKey(autoGenerate = true)
    var symbolId: Long = 0L,

    @ColumnInfo(name = "symbol_name")
    var symbolName: String
)