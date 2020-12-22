package com.lswitaj.moneymanager.network

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

/**
 * DAO for getCandles()
 ** Request example:
 * https://finnhub.io/api/v1/stock/candle?resolution=D&from=1605543327&to=1605629727&token=bv2tbon48v6ru7sfdof0&symbol=IBM
 ** Response example:
 * {
 *  "c":
 *   [118.36,117.7],
 *  "h":
 *   [118.55,118.54],
 *  "l":
 *   [117.12,117.07],
 *  "o":
 *   [118.3,117.6],
 *   "s":
 *    "ok",
 *   "t":
 *    [1605537000,1605623400],
 *   "v":
 *    [5293400,4134500]}
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Candles(
    @Json(name = "c") val closePrice: List<Double>,
    @Json(name = "h") val highPrice: List<Double>,
    @Json(name = "l") val lowPrice: List<Double>,
    @Json(name = "o") val openPrice: List<Double>,
    @Json(name = "s") val status: String,
    @Json(name = "t") val timestamp: List<Long>,
    @Json(name = "v") val volume: List<Long>
) : Parcelable