package com.orangeink.regalia22.login.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("contact") var contact: String? = null
)
