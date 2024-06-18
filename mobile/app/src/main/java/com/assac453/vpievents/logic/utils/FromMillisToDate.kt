package com.assac453.vpievents.logic.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


fun patternToMillis(string: String): Long{
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    val date: Date = sdf.parse(string)
    return date.time
}


fun convertDate(dateInMilliseconds: String, dateFormat: String?): String {
    return dateInMilliseconds
    val millis = patternToMillis(dateInMilliseconds)
    return DateFormat.format(dateFormat, millis).toString()
}

object PATTERN {
    val date = "dd.MM.yyyy HH:mm"
}