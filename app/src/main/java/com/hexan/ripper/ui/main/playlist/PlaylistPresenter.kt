package com.hexan.ripper.ui.login

import com.hexan.daily.ui.base.BasePresenter
import com.hexan.ripper.model.Playlist
import com.hexan.ripper.network.RipperApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by James Cooper on 11/04/2018.
 */
class PlaylistPresenter : BasePresenter<PlaylistMvpView>() {

    val ripperApiService: RipperApiService by lazy { RipperApiService.create() }

    private var playlistSubscribe: Disposable? = null

    private var createPlaylistSubscribe: Disposable? = null

    companion object {
        fun create(): PlaylistPresenter {
            return PlaylistPresenter()
        }
    }

    fun loadPlaylists() {
        if (playlistSubscribe == null || playlistSubscribe?.isDisposed!!) {
            playlistSubscribe = ripperApiService.getPlaylists()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                getMvpView()?.bindPlaylists(result)
                            },
                            { error ->
                                getMvpView()?.showError(error.message)
                            })
        }
    }

    fun createPlaylist(name: String) {
        createPlaylistSubscribe = ripperApiService.createPlaylist(Playlist(name))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            getMvpView()?.bindPlaylistCreated(result)
                        },
                        { error ->
                            getMvpView()?.showError(error.message)
                        })
    }

    override fun detachView() {
        playlistSubscribe?.dispose()
        createPlaylistSubscribe?.dispose()
    }
}