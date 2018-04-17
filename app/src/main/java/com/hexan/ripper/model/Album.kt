package com.hexan.ripper.model

/**
 * Created by James Cooper on 11/04/2018.
 */
class Album(val id: Long?,
            val name: String,
            val coverUrl: String,
            val songs: ArrayList<Song>?){
}