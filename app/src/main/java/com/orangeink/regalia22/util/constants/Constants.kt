package com.orangeink.regalia22.util.constants

import androidx.annotation.StringDef

@StringDef(Constants.SCAN_UNIQUE_ID)
@Retention(AnnotationRetention.SOURCE)
annotation class Constants {
    companion object {
        const val SCAN_UNIQUE_ID = "unique_id"
    }
}
