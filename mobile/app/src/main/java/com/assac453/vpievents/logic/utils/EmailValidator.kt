package com.assac453.vpievents.logic.utils


fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}