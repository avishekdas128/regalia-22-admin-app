package com.orangeink.regalia22.generate.pass.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.generate.pass.data.model.GeneratePassRequest
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneratePassRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun generatePass(
        name: String,
        roll: String,
        email: String,
        allowed: Int,
        amount: String
    ) = getResult {
        service.generatePass(GeneratePassRequest(name, roll, email, allowed, amount))
    }

}