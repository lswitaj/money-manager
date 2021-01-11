package com.lswitaj.moneymanager.utils

import com.lswitaj.moneymanager.data.network.FinnhubApi

//one unix-timestamp month
const val ONE_MONTH_SECONDS: Long = 31 * 60 * 60 * 24

fun getCurrentTimestamp(): String =
    (System.currentTimeMillis() / 1000).toString()

fun getYesterdayTimestamp(): String =
    (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()

//TODO(error handling when the price it's null)
suspend fun getLastClosePrice(symbolName: String): Double = try {
    FinnhubApi.finnhub.getCandles(
        symbolName,
        getYesterdayTimestamp(),
        getCurrentTimestamp()
    ).closePrice.last()
} catch (e: Exception) {
    if (e.message!!.contains("Required value 'closePrice'")) {
        0.0
    } else throw (e)
}

fun displayPrice(price: Double, currency: String): String =
    formatDouble(price) + currency

//TODO(to rethink the approach for big numbers like quantity/price over 6 places)
fun formatDouble(number: Double): String =
    number.toString().take(6)