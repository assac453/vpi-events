package com.assac453.vpievents.logic.registration.user

import com.assac453.vpievents.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun login(email: String, password: String): TokenResponse?{
    val retrofit = Retrofit.Builder()
        .baseUrl(R.string.base_url.toString()) // Замените на базовый URL вашего сервера
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiServiceLogin::class.java)

    return try {
        val response = service.login(email, password)
        if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибки, например, возвращение null или генерация исключения
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}