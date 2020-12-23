package com.lswitaj.moneymanager.utils

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