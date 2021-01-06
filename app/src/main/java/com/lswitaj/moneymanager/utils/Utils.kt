package com.lswitaj.moneymanager.utils

import android.util.Log
import com.lswitaj.moneymanager.data.network.FinnhubApi

const val ONE_MONTH_SECONDS: Long = 31 * 60 * 60 * 24 //one unix-timestamp month

fun getCurrentTimestamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

fun getYesterdayTimestamp(): String {
    return (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()
}

//TODO(error handling when the price it's null)
suspend fun getLastClosePrice(symbolName: String): Double = FinnhubApi.finnhub.getCandles(
    symbolName,
    getYesterdayTimestamp(),
    getCurrentTimestamp()
).closePrice.last()