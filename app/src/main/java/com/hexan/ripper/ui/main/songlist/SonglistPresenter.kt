package com.hexan.ripper.ui.main.songlist

import com.hexan.daily.ui.base.BasePresenter
import com.hexan.ripper.network.RipperApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by James Cooper on 11/04/2018.
 */
class SonglistPresenter : BasePresenter<SonglistMvpView>() {

    val ripperApiService: RipperApiService by lazy { RipperApiService.create() }

    private var songlistSubscribe: Disposable? = null

    private var playlistId: Long? = null

    companion object {
        fun create(): SonglistPresenter {
            return SonglistPresenter()
        }
    }

    fun bindPlaylist(playlistId: Long) {
        this.playlistId = playlistId
    }

    fun loadPlaylistSongs() {
        if (songlistSubscribe == null || songlistSubscribe?.isDisposed!!) {
            songlistSubscribe = ripperApiService.getPlaylistSongs(playlistId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                getMvpView()?.bindSongs(result)
                            },
                            { error ->
                                getMvpView()?.showError(error.message)
                            })
        }
    }

    override fun detachView() {
        songlistSubscribe?.dispose()
    }
}