package com.hexan.ripper.ui.login

import com.hexan.daily.ui.base.BaseMvpView
import com.hexan.ripper.model.Playlist

/**
 * Created by James Cooper on 11/04/2018.
 */
interface PlaylistMvpView : BaseMvpView {
    fun bindPlaylists(result: List<Playlist>)
    fun bindPlaylistCreated(playlist: Playlist)
    fun showError(message: String?)
}