package com.assac453.vpievents.data.entity

import java.time.Instant
import java.util.Date

data class User(
    var id: String,
    var isLoggedIn: Boolean,
    var firstName: String,
    var lastName: String,
    var patronymic: String,
    var phoneNumber: String,
    var email: String,
    var hashPassword: String,
    var birthDay: Date,
    var educationClass: Int,
    var points: Int,
    var token: String,
)

fun getEmptyUser(): User {
    return User(
        "asdsad-asdsad-asdasd",
        false,
        "Aleksey",
        "Vdovenko",
        "Grigorevich",
        "89046723819",
        "ritterfrosch@yandex.ru",
        "hashhashhashhashhashhash",
        Date.from(Instant.now()),
        9,
        0,
        "tokentokentokentokentoken"
    )
}