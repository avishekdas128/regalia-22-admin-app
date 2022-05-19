package com.orangeink.regalia22.generate.free.data.remote

import com.orangeink.regalia22.data.network.RegaliaService
import com.orangeink.regalia22.generate.free.data.model.FreePass
import com.orangeink.regalia22.util.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FreePassRepository @Inject constructor(
    private val service: RegaliaService
) : BaseDataSource() {

    suspend fun studentPass(name: String, roll: String, phone: String) = getResult {
        service.freePass(FreePass(name = name, roll = roll, phone = phone, department = "student"))
    }

    suspend fun alumniPass(
        name: String,
        year: String,
        email: String,
        phone: String,
        department: String
    ) = getResult {
        service.freePass(
            FreePass(
                name = name,
                passingYear = year,
                email = email,
                phone = phone,
                department = department
            )
        )
    }

}