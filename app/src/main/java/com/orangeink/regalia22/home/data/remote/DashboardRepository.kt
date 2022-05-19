package com.orangeink.regalia22.home.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun dashboard() = getResult {
        service.dashboard()
    }

}