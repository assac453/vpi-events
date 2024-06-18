package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class ContactInfoNetwork(

    @SerializedName("telegram_profile" ) var telegramProfile : String? = null,
    @SerializedName("vk_profile"       ) var vkProfile       : String? = null,
    @SerializedName("phone_number"     ) var phoneNumber     : String? = null
)
