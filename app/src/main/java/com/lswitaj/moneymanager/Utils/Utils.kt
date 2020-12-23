package com.lswitaj.moneymanager

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.parse.ParseException

const val ONE_MONTH_SECONDS: Long = 31*60*60*24 //one unix-timestamp month

fun getCurrentTimestamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

fun getYesterdayTimestamp(): String {
    return (System.currentTimeMillis() / 1000 - ONE_MONTH_SECONDS).toString()
}

//TODO(to add email modifier)
//TODO(password validation - without spaces, 8 chars, 1 big, 1 small, 1 number)

fun showSnackbar(view: View?, message: String) {
    view?.hideKeyboard()
    view?.let { Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show() }
}