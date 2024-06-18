package com.assac453.vpievents.logic.registration.user

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServiceLogin {
    @FormUrlEncoded
    @POST("/api/v1/token/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<TokenResponse>
}