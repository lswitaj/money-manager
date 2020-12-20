package com.lswitaj.moneymanager

const val ONE_MONTH_SECONDS: Long = 31*60*60*24 //one unix-timestamp month

fun getCurrentTimestamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

fun getYesterdayTimestamp(): String {
    return (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()
}
