package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class NotificationNetwork (
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("user_id"    ) var userId    : String? = null,
    @SerializedName("title"      ) var title     : String? = null,
    @SerializedName("body"       ) var body      : String? = null,
    @SerializedName("sent_at"    ) var sentAt    : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null

)