package com.hexan.ripper.ui.login

import com.hexan.daily.ui.base.BaseMvpView

/**
 * Created by James Cooper on 11/04/2018.
 */
interface LoginMvpView : BaseMvpView {
    fun loginUser()
    fun showError(message: String?)
}