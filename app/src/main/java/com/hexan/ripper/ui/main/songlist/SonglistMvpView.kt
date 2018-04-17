package com.hexan.ripper.ui.main.songlist

import com.hexan.daily.ui.base.BaseMvpView
import com.hexan.ripper.model.Song

/**
 * Created by James Cooper on 11/04/2018.
 */
interface SonglistMvpView : BaseMvpView {
    fun showError(message: String?)
    fun bindSongs(songList: List<Song>)
}