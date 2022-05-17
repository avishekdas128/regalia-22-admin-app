package com.orangeink.regalia22.verification.data.model

import com.google.gson.annotations.SerializedName

data class Pass(
    @SerializedName("_id") var uniqueId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("roll_number") var rollNumber: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("allowed") var allowed: Int,
    @SerializedName("day_1_validity") var dayOneValidity: String,
    @SerializedName("day_2_validity") var dayTwoValidity: String,
    @SerializedName("count_of_bands_day_1") var countDayOne: Int = 0,
    @SerializedName("count_of_bands_day_2") var countDayTwo: Int = 0,
    @SerializedName("date") var date: String
)
