package com.hexan.ripper.ui.login

import com.hexan.daily.ui.base.BasePresenter
import com.hexan.ripper.manager.SharedPreferenceManager
import com.hexan.ripper.network.RipperApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials

/**
 * Created by James Cooper on 11/04/2018.
 */
class LoginPresenter : BasePresenter<LoginMvpView>() {

    val ripperApiService: RipperApiService = RipperApiService.create()

    private var loginSubscribe: Disposable? = null

    companion object {
        fun create(): LoginPresenter {
            return LoginPresenter()
        }
    }

    fun attemptLogin(username: String, password: String) {
        loginSubscribe = ripperApiService.login(Credentials.basic("49l0tm6ar74tbfq72r5y", "002hal5b8qodc0e36gzb"), "password", username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            SharedPreferenceManager.saveAccessToken(result.access_token)
                            getMvpView()?.loginUser()
                        },
                        { error ->
                            getMvpView()?.showError(error.message)
                        })
    }

    override fun detachView() {
        loginSubscribe?.dispose()
    }

    fun isLoggedIn(): Boolean {
        return !SharedPreferenceManager.getAccessToken().isNullOrEmpty()
    }
}