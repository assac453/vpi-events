package com.assac453.vpievents.logic.qrcode

import java.util.Base64

fun registerOnEvent(result: String): String{
    return try {
        String(Base64.getDecoder().decode(result))
    } catch (e: IllegalArgumentException){
        result
    }
}