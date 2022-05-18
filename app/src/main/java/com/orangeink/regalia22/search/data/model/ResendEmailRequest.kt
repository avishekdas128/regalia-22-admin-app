package com.orangeink.regalia22.search.data.model

import com.google.gson.annotations.SerializedName

data class ResendEmailRequest(
    @SerializedName("email") val email: String,
    @SerializedName("uid") val uid: String
)
