package com.assac453.vpievents.network.model

import com.google.gson.annotations.SerializedName

data class AdditionalInfoNetwork(

    @SerializedName("passport_series"      ) var passportSeries      : String?  = null,
    @SerializedName("passport_number"      ) var passportNumber      : String?  = null,
    @SerializedName("passport_issue_date"  ) var passportIssueDate   : String?  = null,
    @SerializedName("registration_address" ) var registrationAddress : String?  = null,
    @SerializedName("actual_address"       ) var actualAddress       : String?  = null,
    @SerializedName("is_disabled"          ) var isDisabled          : Boolean? = null,
    @SerializedName("is_orphan"            ) var isOrphan            : Boolean? = null
)
