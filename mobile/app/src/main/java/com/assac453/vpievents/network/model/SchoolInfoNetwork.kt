package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class SchoolInfoNetwork(

    @SerializedName("school"     ) var school    : SchoolNetwork? = SchoolNetwork(),
    @SerializedName("class_year" ) var classYear : Int?    = null
)
