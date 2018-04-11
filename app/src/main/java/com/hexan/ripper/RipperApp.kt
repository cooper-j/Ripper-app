package com.hexan.ripper

import android.app.Application
import com.hexan.ripper.manager.SharedPreferenceManager

/**
 * Created by James Cooper on 11/04/2018.
 */
class RipperApp : Application() {


    override fun onCreate() {
        super.onCreate()

        SharedPreferenceManager.init(this)
    }
}