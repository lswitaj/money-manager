package com.lswitaj.moneymanager.utils

//TODO(to consider trimming all whitespaces as it seems to be not necessary)
// function that keeps usernames and emails consistent across the whole app - all lowercased
fun usernameFormatter(username: String?): String? {
    return username?.toLowerCase()
}

fun emailFormatter(email: String?): String? {
    return usernameFormatter(email)
}

fun emailValidator(email: String?): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


fun usernameValidator(username: String?): Boolean {
    val pattern = Regex("(.+){5,31}")
    return username?.matches(pattern) == true
}

fun passwordValidator(password: String?): Boolean {
    val pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,31}\$")
    return password?.matches(pattern) == true
}