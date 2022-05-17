package com.orangeink.regalia22.login.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun login(email: String) = getResult {
        service.login(email)
    }

}