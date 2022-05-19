package com.orangeink.regalia22.home.data.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("label") var label: String? = null,
    @SerializedName("pass_total") var passTotal: Int? = null,
    @SerializedName("unpaid_pass_total") var unpaidPassTotal: Int? = null,
    @SerializedName("total") var total: Int? = null,
    @SerializedName("categorized") var categorized: Categorized = Categorized()
)

data class Categorized(
    @SerializedName("cse_count") var cseCount: Int = 0,
    @SerializedName("ece_count") var eceCount: Int = 0,
    @SerializedName("ee_count") var eeCount: Int = 0,
    @SerializedName("it_count") var itCount: Int = 0,
    @SerializedName("aeie_count") var aeieCount: Int = 0,
    @SerializedName("bca_count") var bcaCount: Int = 0,
    @SerializedName("mca_count") var mcaCount: Int = 0,
    @SerializedName("others") var others: Int = 0
)