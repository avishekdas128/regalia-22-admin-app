package com.orangeink.regalia22.util.constants

import androidx.annotation.StringDef

@StringDef(Role.ROLE_VOLUNTEER, Role.ROLE_COORDINATOR, Role.ROLE_ADMIN)
@Retention(AnnotationRetention.SOURCE)
annotation class Role {
    companion object {
        const val ROLE_VOLUNTEER = "volunteer"
        const val ROLE_COORDINATOR = "coordinator"
        const val ROLE_ADMIN = "admin"
    }
}