package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class SchoolNetwork(

    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("address" ) var address : String? = null
)
