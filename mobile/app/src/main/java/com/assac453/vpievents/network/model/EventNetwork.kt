package com.assac453.vpievents.network.model


import com.google.gson.annotations.SerializedName

data class EventNetwork (
    @SerializedName("id"            ) var id          : String?    = null,
    @SerializedName("event_type_id" ) var eventTypeId : String?    = null,
    @SerializedName("created_at"    ) var createdAt   : String?    = null,
    @SerializedName("updated_at"    ) var updatedAt   : String?    = null,
    @SerializedName("name"          ) var name        : String?    = null,
    @SerializedName("address"       ) var address     : String?    = null,
    @SerializedName("longitude"     ) var longitude   : Double?    = null,
    @SerializedName("latitude"      ) var latitude    : Double?    = null,
    @SerializedName("description"   ) var description : String?    = null,
    @SerializedName("points"        ) var points      : Int?       = null,
    @SerializedName("qrcode"        ) var qrcode      : String?    = null,
    @SerializedName("image"         ) var image       : String?    = null,
    @SerializedName("begin"         ) var begin       : String?    = null,
    @SerializedName("end"           ) var end         : String?    = null,
    @SerializedName("event_type"    ) var eventType   : EventTypeNetwork? = EventTypeNetwork()
)
