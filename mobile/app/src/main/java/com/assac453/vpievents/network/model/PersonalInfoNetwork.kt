package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class PersonalInfoNetwork(

    @SerializedName("birth_date" ) var birthDate  : String? = null,
    @SerializedName("last_name"  ) var lastName   : String? = null,
    @SerializedName("first_name" ) var firstName  : String? = null,
    @SerializedName("patronymic" ) var patronymic : String? = null,
    @SerializedName("gender"     ) var gender     : String? = null
)
