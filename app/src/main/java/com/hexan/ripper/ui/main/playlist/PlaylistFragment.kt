package com.hexan.ripper.ui.main.playlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.hexan.ripper.R
import com.hexan.ripper.model.Playlist
import com.hexan.ripper.ui.GridLayoutDivider
import com.hexan.ripper.ui.login.PlaylistMvpView
import com.hexan.ripper.ui.login.PlaylistPresenter
import com.hexan.ripper.ui.main.BaseFragment
import com.hexan.ripper.ui.main.songlist.SonglistFragment
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.view.*


/**
 * Created by James Cooper on 11/04/2018.
 */
class PlaylistFragment : BaseFragment(), PlaylistMvpView, View.OnClickListener, CreatePlaylistDialogFragment.OnCreatePlaylistListener, OnPlaylistClickListener {

    val playlistAdapter: PlaylistAdapter = PlaylistAdapter(this)

    lateinit var presenter: PlaylistPresenter

    companion object {
        fun newInstance(): PlaylistFragment {
            return PlaylistFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        presenter = PlaylistPresenter.create()
        presenter.attachView(this)
        presenter.loadPlaylists()

        view.playlistRecyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        view.playlistRecyclerView.addItemDecoration(GridLayoutDivider(resources.getDimensionPixelSize(R.dimen.grid_spacing), 2))
        view.playlistRecyclerView.adapter = playlistAdapter

        view.createPlaylistFabButton.setOnClickListener(this)

        view.playlistSwipeRefreshLayout.setOnRefreshListener {
            playlistSwipeRefreshLayout.isRefreshing = true
            presenter.loadPlaylists()
        }

        return view
    }

    override fun bindPlaylists(result: List<Playlist>) {
        playlistSwipeRefreshLayout.isRefreshing = false
        playlistAdapter.bindPlaylists(result)
    }

    override fun bindPlaylistCreated(playlist: Playlist) {
        playlistAdapter.insertPlaylist(playlist)
    }

    override fun showError(message: String?) {
        playlistSwipeRefreshLayout.isRefreshing = false
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        val ft = fragmentManager?.beginTransaction()
        val prev = fragmentManager?.findFragmentByTag("dialog")
        if (prev != null) {
            ft?.remove(prev)
        }
        ft?.addToBackStack(null)

        val newFragment = CreatePlaylistDialogFragment.newInstance(this)
        newFragment.show(ft, "dialog")
    }

    override fun onCreatePlaylist(name: String) {
        presenter.createPlaylist(name)
    }

    override fun onPlaylistClick(playlist: Playlist) {
        createPlaylistFabButton.visibility = View.GONE
        val songlistFragment = SonglistFragment.newInstance(playlist.id!!, playlist.name)
        childFragmentManager.beginTransaction()
                ?.add(R.id.childFragmentLayout, songlistFragment, SonglistFragment::class.java.name)
                ?.addToBackStack(SonglistFragment::class.java.name)
                        ?.commit()
    }

    override fun onResume() {
        super.onResume()
        createPlaylistFabButton.visibility = View.VISIBLE
        setTitle(R.string.title_playlists)
    }
}
