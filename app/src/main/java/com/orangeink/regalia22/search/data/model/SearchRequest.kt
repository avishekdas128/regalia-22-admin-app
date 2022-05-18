package com.orangeink.regalia22.search.data.model

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("roll_no") var roll: String
)
