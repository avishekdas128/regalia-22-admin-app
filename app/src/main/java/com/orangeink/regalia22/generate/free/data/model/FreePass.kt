package com.orangeink.regalia22.generate.free.data.model

import com.google.gson.annotations.SerializedName

data class FreePass(
    @SerializedName("name") var name: String,
    @SerializedName("roll_number") var roll: String = "",
    @SerializedName("passing_year") var passingYear: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("department") var department: String
)
