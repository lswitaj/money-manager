package com.lswitaj.moneymanager

const val ONE_MONTH_SECONDS: Long = 31*60*60*24 //one unix-timestamp month

fun getCurrentTimestamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

fun getYesterdayTimestamp(): String {
    return (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()
}

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
fun usernameModifier(username: String?): String? {
    return username?.toLowerCase()
}

//TODO(to add email modifier)
//TODO(password validation - without spaces, 8 chars, 1 big, 1 small, 1 number)