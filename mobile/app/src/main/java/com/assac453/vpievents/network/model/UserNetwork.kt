package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class UserNetwork (
    @SerializedName("id"                 ) var id                : String? = null,
    @SerializedName("email"              ) var email             : String? = null,
    @SerializedName("email_verified_at"  ) var emailVerifiedAt   : String? = null,
    @SerializedName("verification_token" ) var verificationToken : String? = null,
    @SerializedName("firebase_token"     ) var firebaseToken     : String? = null,
    @SerializedName("points"             ) var points            : Int?    = null,
    @SerializedName("role_id"            ) var roleId            : Int?    = null,
    @SerializedName("created_at"         ) var createdAt         : String? = null,
    @SerializedName("updated_at"         ) var updatedAt         : String? = null
)