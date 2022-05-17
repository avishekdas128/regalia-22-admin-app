package com.orangeink.regalia22.common

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("success") var success: Boolean? = null
)
