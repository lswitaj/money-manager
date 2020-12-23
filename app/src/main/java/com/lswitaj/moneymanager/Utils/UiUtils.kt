package com.lswitaj.moneymanager

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.parse.ParseException

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
fun parseErrorFormatter(error: ParseException?): String? {
    return error?.message?.capitalize()
}

fun showSnackbar(view: View?, message: String) {
    view?.hideKeyboard()
    view?.let { Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show() }
}

fun View.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
// function that keeps usernames consistent across the whole app - all lowercased
fun usernameFormatter(username: String?): String? {
    return username?.toLowerCase()
}

fun emailFormatter(email: String?): String? {
    return usernameFormatter(email)
}

//TODO(to add email modifier)
//TODO(password validation - without spaces, 8 chars, 1 big, 1 small, 1 number)