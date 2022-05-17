package com.orangeink.regalia22.verification.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun scan(id: String) = getResult {
        service.scan(id)
    }

    suspend fun verify(id: String, count: Int) = getResult {
        service.verify(id, count)
    }

}