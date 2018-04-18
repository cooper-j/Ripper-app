package com.hexan.ripper.ui.main.songlist

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hexan.ripper.R
import com.hexan.ripper.model.Song
import com.hexan.ripper.ui.main.BaseFragment
import kotlinx.android.synthetic.main.fragment_songlist.*
import kotlinx.android.synthetic.main.fragment_songlist.view.*
import android.support.v7.widget.DividerItemDecoration



/**
 * Created by James Cooper on 11/04/2018.
 */
class SonglistFragment : BaseFragment(), SonglistMvpView {

    val songlistAdapter: SonglistAdapter = SonglistAdapter()

    lateinit var presenter: SonglistPresenter

    companion object {
        val ARG_PLAYLIST_ID: String = "playlist_id"
        val ARG_PLAYLIST_NAME: String = "playlist_name"

        fun newInstance(playlistId: Long, name: String): SonglistFragment {
            val songlistFragment = SonglistFragment()
            val args = Bundle()
            args.putLong(ARG_PLAYLIST_ID, playlistId)
            args.putString(ARG_PLAYLIST_NAME, name)
            songlistFragment.arguments = args
            return songlistFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_songlist, container, false)

        setTitle(arguments!!.getString(ARG_PLAYLIST_NAME))

        presenter = SonglistPresenter.create()
        presenter.attachView(this)
        presenter.bindPlaylist(arguments!!.getLong(ARG_PLAYLIST_ID))
        presenter.loadPlaylistSongs()

        view.songlistRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.songlistRecyclerView.adapter = songlistAdapter

        val itemDecorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        context?.let { ContextCompat.getDrawable(it, R.drawable.song_divider)?.let { itemDecorator.setDrawable(it) } }
        view.songlistRecyclerView.addItemDecoration(itemDecorator)


        view.songlistSwipeRefreshLayout.setOnRefreshListener {
            songlistSwipeRefreshLayout.isRefreshing = true
            presenter.loadPlaylistSongs()
        }

        return view
    }

    override fun bindSongs(songList: List<Song>) {
        songlistAdapter.bindSongs(songList)
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}