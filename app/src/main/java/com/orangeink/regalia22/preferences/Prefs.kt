package com.orangeink.regalia22.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.orangeink.regalia22.login.data.model.Team

class Prefs(context: Context) {

    private val sharePref: String = "preferences"

    private val userPref: String = "user_pref"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharePref, Context.MODE_PRIVATE)

    fun logout() {
        preferences.edit().clear().apply()
    }

    var user: Team?
        get() = Gson().fromJson(preferences.getString(userPref, null), Team::class.java)
        set(value) = preferences.edit().putString(userPref, Gson().toJson(value)).apply()
}