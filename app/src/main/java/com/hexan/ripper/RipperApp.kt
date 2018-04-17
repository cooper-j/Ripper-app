package com.hexan.ripper

import android.app.Application
import android.content.Intent
import com.hexan.ripper.manager.SharedPreferenceManager
import com.hexan.ripper.network.RxNetworkErrorBus
import com.hexan.ripper.ui.login.LoginActivity
import io.reactivex.functions.Consumer

/**
 * Created by James Cooper on 11/04/2018.
 */
class RipperApp : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPreferenceManager.init(this)
        RxNetworkErrorBus.toObservable()
                .subscribe { event ->
                    when (event) {
                        401 -> {
                            SharedPreferenceManager.saveAccessToken(null)
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                };
    }
}