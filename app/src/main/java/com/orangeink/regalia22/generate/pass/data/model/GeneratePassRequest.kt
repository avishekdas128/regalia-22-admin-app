package com.orangeink.regalia22.generate.pass.data.model

import com.google.gson.annotations.SerializedName

data class GeneratePassRequest(
    @SerializedName("name") var name: String,
    @SerializedName("roll_number") var roll: String,
    @SerializedName("email") var email: String,
    @SerializedName("allowed") var allowed: Int,
    @SerializedName("phone_number") var amount: String,
    @SerializedName("server") var server: Boolean = true,
    @SerializedName("day_1_validity") var dayOne: String = "",
    @SerializedName("day_2_validity") var dayTwo: String = ""
)
