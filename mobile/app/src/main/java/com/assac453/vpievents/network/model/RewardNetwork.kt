package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName


data class RewardNetwork (

    @SerializedName("id"              ) var id             : String? = null,
    @SerializedName("name"            ) var name           : String? = null,
    @SerializedName("points_required" ) var pointsRequired : Int?    = null,
    @SerializedName("image"           ) var image          : String? = null,
    @SerializedName("quantity"        ) var quantity       : Int?    = null,
    @SerializedName("status_id"       ) var statusId       : String? = null,
    @SerializedName("created_at"      ) var createdAt      : String? = null,
    @SerializedName("updated_at"      ) var updatedAt      : String? = null

)
