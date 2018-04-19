package com.hexan.ripper.ui.main.playlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hexan.ripper.R
import com.hexan.ripper.model.Playlist
import com.hexan.ripper.module.GlideApp
import kotlinx.android.synthetic.main.item_playlist.view.*

/**
 * Created by James Cooper on 11/04/2018.
 */
class PlaylistAdapter(private val onPlaylistClickListener: OnPlaylistClickListener) : RecyclerView.Adapter<PlaylistViewHolder>() {

    private val playlists: ArrayList<Playlist> = ArrayList()

    fun bindPlaylists(playlists: List<Playlist>) {
        this.playlists.clear()
        this.playlists.addAll(playlists)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position], onPlaylistClickListener)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false))
    }

    fun insertPlaylist(playlist: Playlist) {
        playlists.add(0, playlist)
        notifyItemInserted(0)
    }
}

interface OnPlaylistClickListener {
    fun onPlaylistClick(playlist: Playlist)
}

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(playlist: Playlist, onPlaylistClickListener: OnPlaylistClickListener) {
        itemView.playlistNameTextView.text = playlist.name
        itemView.songCountTextView.text = playlist.songs?.size.toString() + " songs"
        itemView.setOnClickListener { onPlaylistClickListener.onPlaylistClick(playlist) }
        if (playlist.songs?.isEmpty()!!)
            itemView.playlistItemImageView.setImageResource(R.drawable.default_album_cover)
        else
            GlideApp.with(itemView.context)
                    .load(playlist.songs[0].album.coverUrl)
                    .centerCrop()
                    .into(itemView.playlistItemImageView)
    }
}