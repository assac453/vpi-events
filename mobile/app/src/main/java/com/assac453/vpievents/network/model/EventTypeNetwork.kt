package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName


data class EventTypeNetwork (

    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null

)
