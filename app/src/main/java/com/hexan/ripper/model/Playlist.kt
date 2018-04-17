package com.hexan.ripper.model

/**
 * Created by James Cooper on 11/04/2018.
 */
class Playlist(val id: Long?,
               val name: String,
               val songs: ArrayList<Song>?){
    constructor(name: String) : this(null, name, null)
}