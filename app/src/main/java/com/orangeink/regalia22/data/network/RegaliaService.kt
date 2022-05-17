package com.orangeink.regalia22.data.network

import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.login.data.model.Team
import com.orangeink.regalia22.verification.data.model.Pass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface RegaliaService {

    companion object {
        const val ENDPOINT = "https://regalia-22.herokuapp.com/"
    }

    //Team
    @GET("/teams/{email}")
    suspend fun login(@Path("email") email: String): Response<Team>

    //Scan
    @GET("/scan/{id}")
    suspend fun scan(@Path("id") id: String): Response<Pass>

    //Verify
    @PATCH("/verify/{id}")
    suspend fun verify(
        @Path("id") id: String,
        @Query("count_of_bands") count: Int
    ): Response<SuccessResponse>

}