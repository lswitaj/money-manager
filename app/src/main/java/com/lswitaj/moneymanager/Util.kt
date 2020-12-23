package com.lswitaj.moneymanager

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.parse.ParseException

const val ONE_MONTH_SECONDS: Long = 31*60*60*24 //one unix-timestamp month

fun getCurrentTimestamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

fun getYesterdayTimestamp(): String {
    return (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()
}

fun View.hideKeyboard() {
    //hiding the keyboard
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
fun parseErrorFormatter(error: ParseException?): String? {
    return error?.message?.capitalize()
}

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
fun usernameFormatter(username: String?): String? {
    return username?.toLowerCase()
}

//TODO(to add email modifier)
//TODO(password validation - without spaces, 8 chars, 1 big, 1 small, 1 number)