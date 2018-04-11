package com.hexan.daily.ui.base;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
open class BasePresenter<T : BaseMvpView> {

    var mMvpView : T? = null

    fun attachView(mvpView : T?) {
        mMvpView = mvpView
    }

    open fun detachView() {
        mMvpView = null
    }

    fun getMvpView() : T? {
        return mMvpView
    }
}

