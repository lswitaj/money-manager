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
    // if(closePrice.isNull) the Symbol doesn't exist anymore so that its price can be treated as 0
    if (e.message!!.contains("Required value 'closePrice'")) {
        0.0
    } else throw (e)
}

fun formatPrice(price: Double, currency: String): String =
    formatDouble(price) + currency

//TODO(to rethink the approach for big numbers like quantity/price over 6 places)
fun formatDouble(number: Double): String {
    val formattedNumber = number.toString().take(5)
    return if (formattedNumber.last() == '.') {
        formattedNumber.take(4)
    } else {
        formattedNumber
    }
}

fun countProportion(initialValue : Double, currentValue : Double): String {
    val proportion = ((currentValue/initialValue-1)*100).round(2)
    return when {
        proportion.isInfinite() -> "∞%"
        proportion.isNaN() -> "0%"
        proportion > 0.0 -> "+" + formatDouble(proportion) + "%"
        proportion <= 0.0 -> formatDouble(proportion) + "%"
        else -> {
            throw Exception("Impossible to calculate the profit")
        }
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}