package com.lswitaj.portfelmanager.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * DAO for getSymbolsFromExchange()
 ** Request example:
 * https://finnhub.io/api/v1/stock/symbol?exchange=US&token=bv2tbon48v6ru7sfdof0
 ** Response example:
 * [
 *  {
 *   "currency":"USD",
 *   "description":"AGILENT TECHNOLOGIES INC",
 *   "displaySymbol":"A",
 *   "symbol":"A",
 *   "type":"EQS"
 *  },
 *  {},
 *  ...
 * ]
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Symbol(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String,
    val currency: String
) : Parcelable