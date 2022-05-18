package com.orangeink.regalia22.search.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.search.data.model.ResendEmailRequest
import com.orangeink.regalia22.search.data.model.SearchRequest
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun search(roll: String) = getResult {
        service.search(SearchRequest(roll))
    }

    suspend fun resendEmail(uid: String, email: String) = getResult {
        service.resendMail(ResendEmailRequest(email, uid))
    }

}