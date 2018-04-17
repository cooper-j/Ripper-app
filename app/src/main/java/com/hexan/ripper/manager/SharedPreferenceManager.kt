package com.hexan.ripper.manager

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences

/**
 * Created by James Cooper on 11/04/2018.
 */
class SharedPreferenceManager {

    companion object {

        private val PREFS_ACCESS_TOKEN: String = "bearer_token"

        private var prefs: SharedPreferences? = null

        fun init(context: Context) {
            prefs = context.getSharedPreferences(context.packageName, ContextWrapper.MODE_PRIVATE)
        }

        fun getPreferences(): SharedPreferences {
            if (prefs != null) {
                return prefs as SharedPreferences
            }
            throw RuntimeException(
                    "SharedPreferences not correctly instantiated.")
        }

        fun saveAccessToken(accessToken: String?) {
            getPreferences().edit().putString(PREFS_ACCESS_TOKEN, accessToken).apply()
        }

        fun getAccessToken(): String? {
            return getPreferences().getString(PREFS_ACCESS_TOKEN, null)
        }

    }
}