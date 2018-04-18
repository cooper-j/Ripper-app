package com.hexan.ripper.ui.main.songlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hexan.ripper.R
import com.hexan.ripper.model.Song
import com.hexan.ripper.module.GlideApp
import kotlinx.android.synthetic.main.item_song.view.*

/**
 * Created by James Cooper on 16/04/2018.
 */
class SonglistAdapter : RecyclerView.Adapter<SonglistViewHolder>() {
    private val songs: ArrayList<Song> = ArrayList()

    fun bindSongs(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: SonglistViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SonglistViewHolder {
        return SonglistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false))
    }
}

class SonglistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(song: Song) {
        GlideApp.with(itemView.context)
                .load(song.album.coverUrl)
                .centerCrop()
                .into(itemView.songAlbumCoverImageView)
        itemView.songNameTextView.text = song.name
        itemView.albumNameTextView.text = song.album.name
    }
}